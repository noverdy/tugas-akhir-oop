package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] msgDigest = md.digest(text.getBytes());
            BigInteger no = new BigInteger(1, msgDigest);
            String hash = no.toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static int of(Object object) {
        String string = object.toString();
        int hash = 1;
        for (int i = 0; i < string.length(); i++) {
            if ((int)string.charAt(i) == 0) continue;
            hash *= string.charAt(i);
            hash += string.charAt(i);
            hash %= 10000;
        }
        return hash % 10000;
    }
}
