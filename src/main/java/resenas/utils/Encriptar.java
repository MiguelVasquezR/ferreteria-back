package resenas.utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encriptar {
    public static String encriptar(String text) {
        try {
            SecretKey aesKey = new SecretKeySpec(Utils.PASSWORD_ENCRYPT.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(Utils.CIPHER_INSTAC);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
