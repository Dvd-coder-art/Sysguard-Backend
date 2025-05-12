package com.arquivs.sysguard.security;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesIdEncoder {

    private static String SECRET;
    private static final String ALGORITHM = "AES";

    @Value("${aes.secret}")
    public void setSecret(String secret){
        SECRET = secret;
    }

    public static String encode(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar dados", e);
        }
    }

    public static Long decode(String ecryptedData){
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ecryptedData));
            return Long.parseLong(new String(decrypted));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar dados", e);
        }
    }
}
