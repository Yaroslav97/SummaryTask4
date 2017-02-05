package ua.nure.poliakov.SummaryTask4.utils.encodind;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encoding password by algorytm MD5.
 */

public class Password {

    static public String encodePassword(String password) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        byte[] bytes = messageDigest.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String result = Integer.toHexString(b & 0xff);
            if (result.length() == 1) {
                builder.append('0');
            }
            builder.append(result).append("");
        }
        return builder.toString();
    }
}
