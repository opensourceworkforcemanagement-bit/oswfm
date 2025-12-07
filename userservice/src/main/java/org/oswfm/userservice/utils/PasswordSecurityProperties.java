package org.oswfm.userservice.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Password security configuration following OWASP guidelines
 */
@Configuration
@ConfigurationProperties(prefix = "security.password")
@Data
public class PasswordSecurityProperties {
    
    /**
     * Hashing algorithm to use (BCRYPT, ARGON2, PBKDF2)
     */
    private String algorithm = "BCRYPT";
    
    /**
     * BCrypt cost factor (10-12 recommended by OWASP)
     * Higher = more secure but slower
     */
    private int bcryptStrength = 12;
    
    /**
     * Argon2 memory cost in KB (recommended: 15000-65536)
     */
    private int argon2Memory = 65536;
    
    /**
     * Argon2 iterations (recommended: 2-4)
     */
    private int argon2Iterations = 3;
    
    /**
     * Argon2 parallelism (recommended: 1-4)
     */
    private int argon2Parallelism = 1;
    
    /**
     * Minimum password length (OWASP recommends 8-64)
     */
    private int minLength = 12;
    
    /**
     * Maximum password length (prevent DOS attacks)
     */
    private int maxLength = 128;
    
    /**
     * Require uppercase letters
     */
    private boolean requireUppercase = true;
    
    /**
     * Require lowercase letters
     */
    private boolean requireLowercase = true;
    
    /**
     * Require digits
     */
    private boolean requireDigits = true;
    
    /**
     * Require special characters
     */
    private boolean requireSpecialChars = true;
    
    /**
     * Maximum failed login attempts before account lock
     */
    private int maxFailedAttempts = 5;
    
    /**
     * Account lock duration in minutes
     */
    private int lockDurationMinutes = 30;
    
    /**
     * Password expiration in days (0 = never expires)
     */
    private int expirationDays = 90;
    
    /**
     * Number of previous passwords to remember (prevent reuse)
     */
    private int passwordHistoryCount = 5;
    
    /**
     * Minimum time between password changes in hours
     */
    private int minPasswordAgeHours = 24;
    
    /**
     * Enable account lockout on repeated failures
     */
    private boolean enableAccountLockout = true;
    
    /**
     * Check passwords against common password lists (breach databases)
     */
    private boolean checkCommonPasswords = true;
    
    /**
     * Prevent passwords that contain username
     */
    private boolean preventUsernameInPassword = true;
}
