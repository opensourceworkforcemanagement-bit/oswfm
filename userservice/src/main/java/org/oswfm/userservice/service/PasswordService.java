package org.oswfm.userservice.service;

import org.oswfm.userservice.utils.PasswordSecurityProperties;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.userservice.repository.PasswordCredentialRepository;
import org.oswfm.userservice.repository.UserRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.OffsetDateTime;
import java.util.*;

import org.oswfm.commons.model.user.entity.PasswordCredential;
import org.oswfm.commons.model.user.entity.UserEntity;

/**
 * Password Management Service following OWASP guidelines
 * 
 * Features:
 * - Secure password hashing with bcrypt/argon2
 * - Unique salt per password
 * - Password strength validation
 * - Account lockout on failed attempts
 * - Password expiration
 * - Password history (prevent reuse)
 * - Common password detection
 * - Timing attack prevention
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {
    
    private final PasswordCredentialRepository passwordRepository;
    private final UserRepository userRepository;
    private final PasswordSecurityProperties securityProperties;
    private final ObjectMapper objectMapper;
    
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 32; // 256 bits
    
    // Common weak passwords (in production, use a comprehensive list)
    private static final Set<String> COMMON_PASSWORDS = Set.of(
        "password", "123456", "12345678", "qwerty", "abc123", "monkey",
        "1234567", "letmein", "trustno1", "dragon", "baseball", "iloveyou",
        "master", "sunshine", "ashley", "bailey", "passw0rd", "shadow",
        "123123", "654321", "superman", "qazwsx", "michael", "football"
    );

    
    /**
     * Create new password credential for a user
     */
    @Transactional
    public PasswordCredential createPassword(Integer userId, String plainPassword, 
                                            boolean mustChangePassword) {
        log.info("Creating password for user: {}", userId);
        
        // Validate user exists
        UserEntity user = userRepository.findById(userId.intValue())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Check if password already exists
        if (passwordRepository.existsByUser_UserId(userId)) {
            throw new IllegalStateException("Password already exists for user: " + userId);
        }
        
        // Validate password strength
        PasswordStrengthResult strength = validatePasswordStrength(plainPassword, user.getUserName());
        if (!strength.isValid()) {
            throw new IllegalArgumentException("Password does not meet security requirements: " + 
                String.join(", ", strength.getViolations()));
        }
        
        // Generate salt
        String salt = generateSalt();
        
        // Hash password with salt
        String passwordHash = hashPassword(plainPassword, salt);
        
        // Create credential
        PasswordCredential credential = new PasswordCredential();
        credential.setUser(user);
        credential.setPasswordHash(passwordHash);
        credential.setSalt(salt);
        credential.setAlgorithm(securityProperties.getAlgorithm());
        credential.setAlgorithmParams(getAlgorithmParams());
        credential.setFailedAttempts(0);
        credential.setMustChangePassword(mustChangePassword);
        credential.setLastChangedAt(OffsetDateTime.now());
        
        // Set expiration if configured
        if (securityProperties.getExpirationDays() > 0) {
            credential.setExpiresAt(
                OffsetDateTime.now().plusDays(securityProperties.getExpirationDays())
            );
        }
        
        // Initialize password history
        credential.setPasswordHistory(serializePasswordHistory(
            Collections.singletonList(passwordHash)
        ));
        
        return passwordRepository.save(credential);
    }
    
    /**
     * Change user password
     */
    @Transactional
    public void changePassword(Integer userId, String currentPassword, String newPassword) {
        log.info("Changing password for user: {}", userId);
        
        // Get existing credential
        PasswordCredential credential = passwordRepository.findByUser_UserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Password credential not found for user", 
                "userId", userId));
        
        // Check if account is locked
        if (credential.isLocked()) {
            throw new IllegalStateException("Account is locked until: " + credential.getLockedUntil());
        }
        
        // Verify current password
        if (!verifyPassword(currentPassword, credential.getPasswordHash(), credential.getSalt())) {
            handleFailedPasswordVerification(credential);
            throw new IllegalArgumentException("Current password is incorrect");
        }
        
        // Check minimum password age
        if (credential.getLastChangedAt() != null) {
            OffsetDateTime minChangeTime = credential.getLastChangedAt()
                .plusHours(securityProperties.getMinPasswordAgeHours());
            if (OffsetDateTime.now().isBefore(minChangeTime)) {
                throw new IllegalStateException(
                    "Password was recently changed. Please wait until: " + minChangeTime
                );
            }
        }
        
        // Validate new password strength
        UserEntity user = credential.getUser();
        PasswordStrengthResult strength = validatePasswordStrength(newPassword, user.getUserName());
        if (!strength.isValid()) {
            throw new IllegalArgumentException("New password does not meet security requirements: " + 
                String.join(", ", strength.getViolations()));
        }
        
        // Check password history
        List<String> history = deserializePasswordHistory(credential.getPasswordHistory());
        for (String oldHash : history) {
            if (verifyPassword(newPassword, oldHash, credential.getSalt())) {
                throw new IllegalArgumentException(
                    "Password was recently used. Please choose a different password."
                );
            }
        }
        
        // Generate new salt for additional security
        String newSalt = generateSalt();
        
        // Hash new password
        String newPasswordHash = hashPassword(newPassword, newSalt);
        
        // Update credential
        credential.setPasswordHash(newPasswordHash);
        credential.setSalt(newSalt);
        credential.setLastChangedAt(OffsetDateTime.now());
        credential.setMustChangePassword(false);
        credential.setFailedAttempts(0);
        credential.setLockedUntil(null);
        
        // Update expiration
        if (securityProperties.getExpirationDays() > 0) {
            credential.setExpiresAt(
                OffsetDateTime.now().plusDays(securityProperties.getExpirationDays())
            );
        }
        
        // Update password history
        history.add(0, newPasswordHash);
        if (history.size() > securityProperties.getPasswordHistoryCount()) {
            history = history.subList(0, securityProperties.getPasswordHistoryCount());
        }
        credential.setPasswordHistory(serializePasswordHistory(history));
        
        passwordRepository.save(credential);
        
        log.info("Password changed successfully for user: {}", userId);
    }
    
    /**
     * Reset password (admin or forgot password flow)
     */
    @Transactional
    public void resetPassword(Integer userId, String newPassword) {
        log.info("Resetting password for user: {}", userId);
        
        PasswordCredential credential = passwordRepository.findByUser_UserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Password credential not found for user", 
                "userId", userId));
        
        // Validate new password strength
        UserEntity user = credential.getUser();
        PasswordStrengthResult strength = validatePasswordStrength(newPassword, user.getUserName());
        if (!strength.isValid()) {
            throw new IllegalArgumentException("New password does not meet security requirements: " + 
                String.join(", ", strength.getViolations()));
        }
        
        // Generate new salt
        String newSalt = generateSalt();
        
        // Hash new password
        String newPasswordHash = hashPassword(newPassword, newSalt);
        
        // Update credential
        credential.setPasswordHash(newPasswordHash);
        credential.setSalt(newSalt);
        credential.setLastChangedAt(OffsetDateTime.now());
        credential.setMustChangePassword(true); // Require change on next login
        credential.setFailedAttempts(0);
        credential.setLockedUntil(null);
        
        // Update expiration
        if (securityProperties.getExpirationDays() > 0) {
            credential.setExpiresAt(
                OffsetDateTime.now().plusDays(securityProperties.getExpirationDays())
            );
        }
        
        // Clear password history on reset
        credential.setPasswordHistory(serializePasswordHistory(
            Collections.singletonList(newPasswordHash)
        ));
        
        passwordRepository.save(credential);
        
        log.info("Password reset successfully for user: {}", userId);
    }
    
    // ========================================================================
    // Password Verification
    // ========================================================================
    
    /**
     * Verify password matches stored credential
     */
    @Transactional
    public PasswordVerificationResult verifyPasswordCredential(String userName, String password) {
        log.debug("Verifying password for userName: {}", userName);
        
        PasswordCredential credential = passwordRepository.findByUser_UserName(userName);
        
        // Prevent timing attacks - always hash even if user doesn't exist
        if (credential == null) {
            // Dummy hash to prevent timing attacks
            hashPassword(password, generateSalt());
            return PasswordVerificationResult.invalid("Invalid userName or password");
        }
        
        // Check if account is locked
        if (credential.isLocked()) {
            return PasswordVerificationResult.locked(
                "Account is locked until: " + credential.getLockedUntil(),
                credential.getLockedUntil(),
                credential.getFailedAttempts()
            );
        }
        
        // Check if password expired
        if (credential.isPasswordExpired()) {
            return PasswordVerificationResult.expired(
                "Password has expired. Please reset your password.",
                credential.getExpiresAt()
            );
        }
        
        // Verify password
        boolean valid = verifyPassword(password, credential.getPasswordHash(), credential.getSalt());
        
        if (valid) {
            // Reset failed attempts on successful login
            resetFailedAttempts(credential);
            
            // Check if must change password
            if (credential.getMustChangePassword()) {
                return PasswordVerificationResult.mustChange(
                    "Password change required"
                );
            }
            
            return PasswordVerificationResult.valid("Password verified successfully");
        } else {
            // Handle failed attempt
            handleFailedPasswordVerification(credential);
            return PasswordVerificationResult.invalid("Invalid userName or password");
        }
    }
    
    /**
     * Verify password hash matches (internal use)
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword, String salt) {
        String algorithm = securityProperties.getAlgorithm();
        String combined = plainPassword + salt;

        switch (algorithm.toUpperCase()) {
            case "BCRYPT":
                BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(
                    securityProperties.getBcryptStrength(),
                    SECURE_RANDOM
                );
                return bcryptEncoder.matches(combined, hashedPassword);

            case "ARGON2":
                Argon2PasswordEncoder argon2Encoder = new Argon2PasswordEncoder(
                    16,
                    32,
                    securityProperties.getArgon2Parallelism(),
                    securityProperties.getArgon2Memory(),
                    securityProperties.getArgon2Iterations()
                );
                return argon2Encoder.matches(combined, hashedPassword);

            case "PBKDF2":
                // PBKDF2 is deterministic with the same salt, so direct comparison works
                String computedHash = hashWithPbkdf2(plainPassword, salt);
                return constantTimeEquals(computedHash, hashedPassword);

            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }
    
    // ========================================================================
    // Password Hashing
    // ========================================================================
    
    /**
     * Hash password using configured algorithm
     */
    private String hashPassword(String password, String salt) {
        String algorithm = securityProperties.getAlgorithm();
        
        //TODO: enum for algorithms

        switch (algorithm.toUpperCase()) {
            case "BCRYPT":
                return hashWithBcrypt(password, salt);
            case "ARGON2":
                return hashWithArgon2(password, salt);
            case "PBKDF2":
                return hashWithPbkdf2(password, salt);
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }
    
    /**
     * Hash with BCrypt (recommended by OWASP)
     */
    private String hashWithBcrypt(String password, String salt) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(
            securityProperties.getBcryptStrength(),
            SECURE_RANDOM
        );
        
        // Combine password and salt
        String combined = password + salt;
        return encoder.encode(combined);
    }
    
    /**
     * Hash with Argon2 (most secure, OWASP recommended for new systems)
     */
    private String hashWithArgon2(String password, String salt) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(
            16, // salt length
            32, // hash length
            securityProperties.getArgon2Parallelism(),
            securityProperties.getArgon2Memory(),
            securityProperties.getArgon2Iterations()
        );
        
        String combined = password + salt;
        return encoder.encode(combined);
    }
    
    /**
     * Hash with PBKDF2 (good alternative, widely supported)
     */
    private String hashWithPbkdf2(String password, String salt) {
        try {
            int iterations = 310000; // OWASP recommended minimum
            int keyLength = 256;
            
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            
            PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                iterations,
                keyLength
            );
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password with PBKDF2", e);
        }
    }
    
    /**
     * Generate cryptographically secure random salt
     */
    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    // ========================================================================
    // Password Strength Validation
    // ========================================================================
    
    /**
     * Validate password meets security requirements
     */
    public PasswordStrengthResult validatePasswordStrength(String password, String userName) {
        List<String> violations = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
        
        // Check length
        if (password.length() < securityProperties.getMinLength()) {
            violations.add("Password must be at least " + securityProperties.getMinLength() + 
                " characters long");
        }
        
        if (password.length() > securityProperties.getMaxLength()) {
            violations.add("Password must not exceed " + securityProperties.getMaxLength() + 
                " characters");
        }
        
        // Check character requirements
        if (securityProperties.isRequireUppercase() && !password.matches(".*[A-Z].*")) {
            violations.add("Password must contain at least one uppercase letter");
            suggestions.add("Add an uppercase letter (A-Z)");
        }
        
        if (securityProperties.isRequireLowercase() && !password.matches(".*[a-z].*")) {
            violations.add("Password must contain at least one lowercase letter");
            suggestions.add("Add a lowercase letter (a-z)");
        }
        
        if (securityProperties.isRequireDigits() && !password.matches(".*\\d.*")) {
            violations.add("Password must contain at least one digit");
            suggestions.add("Add a number (0-9)");
        }
        
        if (securityProperties.isRequireSpecialChars() && 
            !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            violations.add("Password must contain at least one special character");
            suggestions.add("Add a special character (!@#$%^&*...)");
        }
        
        // Check for common passwords
        if (securityProperties.isCheckCommonPasswords() && 
            COMMON_PASSWORDS.contains(password.toLowerCase())) {
            violations.add("Password is too common and easily guessable");
            suggestions.add("Choose a unique password");
        }
        
        // Check if password contains userName
        if (securityProperties.isPreventUsernameInPassword() && 
            userName != null && 
            password.toLowerCase().contains(userName.toLowerCase())) {
            violations.add("Password must not contain your userName");
            suggestions.add("Remove userName from password");
        }
        
        // Check for sequential characters (123, abc)
        if (containsSequentialChars(password, 3)) {
            violations.add("Password contains sequential characters");
            suggestions.add("Avoid sequences like '123' or 'abc'");
        }
        
        // Check for repeated characters (aaa, 111)
        if (containsRepeatedChars(password, 3)) {
            violations.add("Password contains repeated characters");
            suggestions.add("Avoid repetition like 'aaa' or '111'");
        }
        
        // Calculate strength score (0-100)
        int strength = calculatePasswordStrength(password);
        String strengthLevel = getStrengthLevel(strength);
        
        boolean valid = violations.isEmpty() && strength >= 60;
        String message = valid ? "Password meets security requirements" : 
            "Password does not meet security requirements";
        
        return new PasswordStrengthResult(valid, strength, strengthLevel, message, 
            violations, suggestions);
    }
    
    /**
     * Calculate password strength score (0-100)
     */
    private int calculatePasswordStrength(String password) {
        int score = 0;
        
        // Length (up to 30 points)
        score += Math.min(password.length() * 2, 30);
        
        // Character variety (up to 40 points)
        if (password.matches(".*[a-z].*")) score += 10;
        if (password.matches(".*[A-Z].*")) score += 10;
        if (password.matches(".*\\d.*")) score += 10;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) score += 10;
        
        // Entropy (up to 30 points)
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : password.toCharArray()) {
            uniqueChars.add(c);
        }
        score += Math.min(uniqueChars.size() * 2, 30);
        
        return Math.min(score, 100);
    }
    
    private String getStrengthLevel(int score) {
        if (score >= 90) return "VERY_STRONG";
        if (score >= 75) return "STRONG";
        if (score >= 60) return "GOOD";
        if (score >= 40) return "FAIR";
        return "WEAK";
    }
    
    private boolean containsSequentialChars(String password, int minLength) {
        for (int i = 0; i <= password.length() - minLength; i++) {
            boolean sequential = true;
            for (int j = 1; j < minLength; j++) {
                if (password.charAt(i + j) != password.charAt(i + j - 1) + 1) {
                    sequential = false;
                    break;
                }
            }
            if (sequential) return true;
        }
        return false;
    }
    
    private boolean containsRepeatedChars(String password, int minLength) {
        for (int i = 0; i <= password.length() - minLength; i++) {
            boolean repeated = true;
            for (int j = 1; j < minLength; j++) {
                if (password.charAt(i + j) != password.charAt(i)) {
                    repeated = false;
                    break;
                }
            }
            if (repeated) return true;
        }
        return false;
    }
    
    // ========================================================================
    // Account Lockout Management
    // ========================================================================
    
    private void handleFailedPasswordVerification(PasswordCredential credential) {
        if (!securityProperties.isEnableAccountLockout()) {
            return;
        }
        
        credential.setFailedAttempts(credential.getFailedAttempts() + 1);
        
        if (credential.getFailedAttempts() >= securityProperties.getMaxFailedAttempts()) {
            OffsetDateTime lockUntil = OffsetDateTime.now()
                .plusMinutes(securityProperties.getLockDurationMinutes());
            credential.setLockedUntil(lockUntil);
            
            log.warn("Account locked for user {} until {}", 
                credential.getUser().getUserName(), lockUntil);
        }
        
        passwordRepository.save(credential);
    }
    
    private void resetFailedAttempts(PasswordCredential credential) {
        credential.setFailedAttempts(0);
        credential.setLockedUntil(null);
        passwordRepository.save(credential);
    }
    
    // ========================================================================
    // Utility Methods
    // ========================================================================
    
    /**
     * Constant-time string comparison to prevent timing attacks
     */
    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return a == b;
        }
        
        if (a.length() != b.length()) {
            return false;
        }
        
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        
        return result == 0;
    }
    
    private String getAlgorithmParams() {
        switch (securityProperties.getAlgorithm().toUpperCase()) {
            case "BCRYPT":
                return "strength=" + securityProperties.getBcryptStrength();
            case "ARGON2":
                return String.format("memory=%d,iterations=%d,parallelism=%d",
                    securityProperties.getArgon2Memory(),
                    securityProperties.getArgon2Iterations(),
                    securityProperties.getArgon2Parallelism());
            case "PBKDF2":
                return "iterations=310000";
            default:
                return "";
        }
    }
    
    private String serializePasswordHistory(List<String> history) {
        try {
            return objectMapper.writeValueAsString(history);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing password history", e);
        }
    }
    
    private List<String> deserializePasswordHistory(String historyJson) {
        if (historyJson == null || historyJson.isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return objectMapper.readValue(historyJson, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error deserializing password history", e);
            return new ArrayList<>();
        }
    }
    
    // ========================================================================
    // Inner Classes
    // ========================================================================
    
    public static class PasswordStrengthResult {
        private final boolean valid;
        private final int strength;
        private final String strengthLevel;
        private final String message;
        private final List<String> violations;
        private final List<String> suggestions;
        
        public PasswordStrengthResult(boolean valid, int strength, String strengthLevel,
                                     String message, List<String> violations, 
                                     List<String> suggestions) {
            this.valid = valid;
            this.strength = strength;
            this.strengthLevel = strengthLevel;
            this.message = message;
            this.violations = violations;
            this.suggestions = suggestions;
        }
        
        public boolean isValid() { return valid; }
        public int getStrength() { return strength; }
        public String getStrengthLevel() { return strengthLevel; }
        public String getMessage() { return message; }
        public List<String> getViolations() { return violations; }
        public List<String> getSuggestions() { return suggestions; }
    }
    
    public static class PasswordVerificationResult {
        private final boolean valid;
        private final String message;
        private final boolean accountLocked;
        private final boolean passwordExpired;
        private final boolean mustChangePassword;
        private final OffsetDateTime lockedUntil;
        private final OffsetDateTime expiresAt;
        private final int failedAttempts;
        
        private PasswordVerificationResult(boolean valid, String message, boolean accountLocked,
                                          boolean passwordExpired, boolean mustChangePassword,
                                          OffsetDateTime lockedUntil, OffsetDateTime expiresAt,
                                          int failedAttempts) {
            this.valid = valid;
            this.message = message;
            this.accountLocked = accountLocked;
            this.passwordExpired = passwordExpired;
            this.mustChangePassword = mustChangePassword;
            this.lockedUntil = lockedUntil;
            this.expiresAt = expiresAt;
            this.failedAttempts = failedAttempts;
        }
        
        public static PasswordVerificationResult valid(String message) {
            return new PasswordVerificationResult(true, message, false, false, false, 
                null, null, 0);
        }
        
        public static PasswordVerificationResult invalid(String message) {
            return new PasswordVerificationResult(false, message, false, false, false, 
                null, null, 0);
        }
        
        public static PasswordVerificationResult locked(String message, OffsetDateTime lockedUntil,
                                                       int failedAttempts) {
            return new PasswordVerificationResult(false, message, true, false, false, 
                lockedUntil, null, failedAttempts);
        }
        
        public static PasswordVerificationResult expired(String message, OffsetDateTime expiresAt) {
            return new PasswordVerificationResult(false, message, false, true, false, 
                null, expiresAt, 0);
        }
        
        public static PasswordVerificationResult mustChange(String message) {
            return new PasswordVerificationResult(false, message, false, false, true, 
                null, null, 0);
        }
        
        // Getters
        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
        public boolean isAccountLocked() { return accountLocked; }
        public boolean isPasswordExpired() { return passwordExpired; }
        public boolean isMustChangePassword() { return mustChangePassword; }
        public OffsetDateTime getLockedUntil() { return lockedUntil; }
        public OffsetDateTime getExpiresAt() { return expiresAt; }
        public int getFailedAttempts() { return failedAttempts; }
    }
}
