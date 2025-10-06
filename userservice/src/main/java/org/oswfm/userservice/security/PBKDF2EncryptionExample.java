package org.oswfm.userservice.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBKDF2EncryptionExample {
    
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_LENGTH = 256; // bits
    private static final int IV_LENGTH = 12; // bytes for GCM
    private static final int SALT_LENGTH = 16; // bytes
    private static final int ITERATIONS = 100000; // OWASP recommended minimum
    private static final int TAG_LENGTH = 16; // bytes for GCM authentication tag
    
    /**
     * Derives an encryption key from a password using PBKDF2
     * 
     * @param password The password to derive key from
     * @param salt Random salt for key derivation
     * @param iterations Number of PBKDF2 iterations
     * @return SecretKey derived from password
     */
    public static SecretKey deriveKeyFromPassword(char[] password, byte[] salt, int iterations) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, iterations, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }
    
    /**
     * Generates a cryptographically secure random salt
     * 
     * @return byte array containing random salt
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Generates a random initialization vector for AES-GCM
     * 
     * @return byte array containing random IV
     */
    public static byte[] generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);
        return iv;
    }
    
    /**
     * Encrypts plaintext using password-based encryption with PBKDF2
     * 
     * @param plaintext The text to encrypt
     * @param password The password for encryption
     * @return EncryptionResult containing encrypted data and metadata
     */
    public static EncryptionResult encrypt(String plaintext, char[] password) throws Exception {
        // Generate salt and IV
        byte[] salt = generateSalt();
        byte[] iv = generateIV();
        
        // Derive key from password
        SecretKey key = deriveKeyFromPassword(password, salt, ITERATIONS);
        
        // Initialize cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
        
        // Encrypt the plaintext
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes("UTF-8"));
        
        return new EncryptionResult(encryptedData, salt, iv);
    }
    
    /**
     * Decrypts ciphertext using password-based encryption with PBKDF2
     * 
     * @param encryptionResult The encryption result containing encrypted data and metadata
     * @param password The password for decryption
     * @return Decrypted plaintext
     */
    public static String decrypt(EncryptionResult encryptionResult, char[] password) throws Exception {
        // Derive the same key from password and salt
        SecretKey key = deriveKeyFromPassword(password, encryptionResult.salt, ITERATIONS);
        
        // Initialize cipher for decryption
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH * 8, encryptionResult.iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        
        // Decrypt the data
        byte[] decryptedData = cipher.doFinal(encryptionResult.encryptedData);
        
        return new String(decryptedData, "UTF-8");
    }
    
    /**
     * Container class for encryption results
     */
    public static class EncryptionResult {
        public final byte[] encryptedData;
        public final byte[] salt;
        public final byte[] iv;
        
        public EncryptionResult(byte[] encryptedData, byte[] salt, byte[] iv) {
            this.encryptedData = encryptedData;
            this.salt = salt;
            this.iv = iv;
        }
        
        /**
         * Converts the encryption result to a Base64 string for storage
         * Format: salt:iv:encryptedData (all Base64 encoded)
         */
        public String toBase64String() {
            String saltB64 = Base64.getEncoder().encodeToString(salt);
            String ivB64 = Base64.getEncoder().encodeToString(iv);
            String dataB64 = Base64.getEncoder().encodeToString(encryptedData);
            return saltB64 + ":" + ivB64 + ":" + dataB64;
        }
        
        /**
         * Creates an EncryptionResult from a Base64 string
         */
        public static EncryptionResult fromBase64String(String base64String) {
            String[] parts = base64String.split(":");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid format for encrypted data");
            }
            
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] iv = Base64.getDecoder().decode(parts[1]);
            byte[] encryptedData = Base64.getDecoder().decode(parts[2]);
            
            return new EncryptionResult(encryptedData, salt, iv);
        }
    }
    
    /**
     * Example usage and demonstration
     */
    public static void main(String[] args) {
        try {
            // Example sensitive data
            String sensitiveData = "SSN: 123-45-6789";
            char[] password = "MySecurePassword123!".toCharArray();
            
            System.out.println("Original data: " + sensitiveData);
            System.out.println("Password: " + new String(password));
            System.out.println();
            
            // Encrypt the data
            EncryptionResult result = encrypt(sensitiveData, password);
            String encodedResult = result.toBase64String();
            
            System.out.println("Encrypted and encoded: " + encodedResult);
            System.out.println("Salt (Base64): " + Base64.getEncoder().encodeToString(result.salt));
            System.out.println("IV (Base64): " + Base64.getEncoder().encodeToString(result.iv));
            System.out.println();
            
            // Decrypt the data
            EncryptionResult decodedResult = EncryptionResult.fromBase64String(encodedResult);
            String decryptedData = decrypt(decodedResult, password);
            
            System.out.println("Decrypted data: " + decryptedData);
            System.out.println("Decryption successful: " + sensitiveData.equals(decryptedData));
            
            // Clear password from memory (security best practice)
            java.util.Arrays.fill(password, '\0');
            
            // Demonstrate wrong password
            System.out.println();
            System.out.println("--- Testing with wrong password ---");
            char[] wrongPassword = "WrongPassword".toCharArray();
            try {
                decrypt(decodedResult, wrongPassword);
            } catch (Exception e) {
                System.out.println("Decryption with wrong password failed as expected: " + 
                    e.getClass().getSimpleName());
            }
            java.util.Arrays.fill(wrongPassword, '\0');
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}