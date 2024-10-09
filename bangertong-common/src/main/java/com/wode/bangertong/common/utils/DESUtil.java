package com.wode.bangertong.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DESUtil {
//    public static void main(String[] args) throws Exception {
//        String key = "H7jh(2-b"; // DES密钥必须为8字节长
//        String plaintext = "Hello, World!";
//        //加密
//        String encrypted = encrypt(key, plaintext);
//        System.out.println("Encrypted: " + encrypted);
//
//        //解密
//        String decrypted = decrypt(key, encrypted);
//        System.out.println("Decrypted: " + decrypted);
//    }
    public static String encrypt(String key, String plaintext) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKeySpec);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    public static String decrypt(String key, String ciphertext) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKeySpec);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}