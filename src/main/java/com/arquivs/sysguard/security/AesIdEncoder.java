package com.arquivs.sysguard.security;

        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Component;

        import javax.crypto.Cipher;
        import javax.crypto.spec.SecretKeySpec;
        import java.util.Base64;

        @Component
        public class AesIdEncoder {

            private final String secret;
            private static final String ALGORITHM = "AES";

            public AesIdEncoder(@Value("${aes.secret}") String secret) {
                this.secret = secret;
            }

            public String encode(String data) {
                try {
                    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), ALGORITHM);
                    Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    byte[] encrypted = cipher.doFinal(data.getBytes());
                    return Base64.getEncoder().encodeToString(encrypted);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao criptografar dados", e);
                }
            }

            public Long decode(String encryptedData){
                try {
                    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), ALGORITHM);
                    Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
                    return Long.parseLong(new String(decrypted));
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao descriptografar dados", e);
                }
            }
        }