package tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class Encoder {
    public static String encrypt(String arg) {
        try {
            MessageDigest mg = MessageDigest.getInstance("SHA-224");
            byte[] messageBytes = mg.digest(arg.getBytes());
            BigInteger bigInteger = new BigInteger(1, messageBytes);
            String hashtext = bigInteger.toString();
            return hashtext;
        } catch(NoSuchAlgorithmException e) {
            System.out.println("Произошла ошибка при кодировании.");
            ClientLogger.logger.log(Level.WARNING, "Ошибка при кодировании сообщения.");
        }
        return null;
    }
}
