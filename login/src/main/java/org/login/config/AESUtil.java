//package org.login.config;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import javax.crypto.spec.IvParameterSpec;
//import java.util.Base64;
//import java.nio.charset.StandardCharsets;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import java.util.Arrays;
//import java.security.SecureRandom;
//
//@Component
//public class AESUtil {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    // Generates a 16-byte key from the secret key (AES-128)
//    private byte[] getValidKey() {
//        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
//        return Arrays.copyOf(keyBytes, 16); // AES-128 requires a 16-byte key
//    }
//
//    // Generates a random Initialization Vector (IV) for AES encryption
//    private IvParameterSpec getIV() {
//        byte[] iv = new byte[16]; // 16-byte IV for AES
//        new SecureRandom().nextBytes(iv); // Fill the IV with random bytes
//        return new IvParameterSpec(iv);
//    }
//
//    // Encrypts the plain text using AES/CBC/PKCS5Padding
//    public String encryptAES(String plainText) throws Exception {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(getValidKey(), "AES");
//        IvParameterSpec ivParameterSpec = getIV(); // Generate IV for CBC mode
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // AES mode with padding and CBC
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec); // Initialize cipher with secret key and IV
//
//        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
//
//        // Combine IV and encrypted data, then encode to Base64
//        byte[] combined = new byte[ivParameterSpec.getIV().length + encryptedBytes.length];
//        System.arraycopy(ivParameterSpec.getIV(), 0, combined, 0, ivParameterSpec.getIV().length);
//        System.arraycopy(encryptedBytes, 0, combined, ivParameterSpec.getIV().length, encryptedBytes.length);
//
//        return Base64.getEncoder().encodeToString(combined); // Base64 encode the combined result
//    }
//
//    // Decrypts the encrypted text using AES/CBC/PKCS5Padding
//    public String decryptAES(String encryptedText) throws Exception {
//        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText); // Decode from Base64
//
//        // Extract the IV and encrypted data from the decoded bytes
//        byte[] iv = Arrays.copyOfRange(decodedBytes, 0, 16); // The first 16 bytes are the IV
//        byte[] encryptedData = Arrays.copyOfRange(decodedBytes, 16, decodedBytes.length); // The rest is the encrypted data
//
//        SecretKeySpec secretKeySpec = new SecretKeySpec(getValidKey(), "AES");
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv); // Initialize IV with extracted bytes
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Same mode as encryption
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec); // Initialize cipher for decryption
//
//        byte[] decryptedBytes = cipher.doFinal(encryptedData); // Decrypt the data
//
//        return new String(decryptedBytes, StandardCharsets.UTF_8); // Return the decrypted data as a string
//    }
//
//}
