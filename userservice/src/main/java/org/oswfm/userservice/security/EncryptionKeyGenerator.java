package org.oswfm.userservice.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionKeyGenerator {
    
    /**
     * Generates a unique AES-256 encryption key suitable for encrypting sensitive data
     * like passwords or SSNs.
     * 
     * @return SecretKey - A 256-bit AES encryption key
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static SecretKey generateEncryptionKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 256-bit key for strong encryption
        return keyGenerator.generateKey();
    }
    
    /**
     * Generates a unique encryption key and returns it as a Base64 encoded string
     * for easy storage and transmission.
     * 
     * @return String - Base64 encoded encryption key
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static String generateEncryptionKeyAsString() throws NoSuchAlgorithmException {
        SecretKey key = generateEncryptionKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    
    /**
     * Creates a SecretKey from a Base64 encoded string
     * 
     * @param encodedKey Base64 encoded key string
     * @return SecretKey reconstructed from the encoded string
     */
    public static SecretKey createKeyFromString(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "AES");
    }
    
    /**
     * Alternative method to generate a key using SecureRandom for additional entropy
     * 
     * @return SecretKey - A 256-bit AES encryption key
     */
    public static SecretKey generateEncryptionKeyWithSecureRandom() {
        byte[] keyBytes = new byte[32]; // 32 bytes = 256 bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "AES");
    }
    
    /**
     * Example usage and testing method
     */
    public static void main(String[] args) {
        try {
            // Generate a key
            SecretKey key = generateEncryptionKey();
            System.out.println("Generated key algorithm: " + key.getAlgorithm());
            System.out.println("Key format: " + key.getFormat());
            System.out.println("Key length: " + key.getEncoded().length * 8 + " bits");
            
            // Generate key as string
            String keyString = generateEncryptionKeyAsString();
            System.out.println("Key as Base64 string: " + keyString);
            
            // Recreate key from string
            SecretKey recreatedKey = createKeyFromString(keyString);
            System.out.println("Recreated key matches: " + 
                java.util.Arrays.equals(key.getEncoded(), recreatedKey.getEncoded()));
            
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error generating encryption key: " + e.getMessage());
        }
    }
}