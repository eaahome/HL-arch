package name.erzin.learn.hl.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// NOTE: salt is hardcoded and not stored in the DB
public class SecurityProvider {
    private SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    public SecurityProvider() throws NoSuchAlgorithmException {
    }

    /**
     * Prepares password for storing in the DB
     * @param plainPassword - password from Web Form
     * @return Password hash, encoded as Base64, ready to store into DB
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public String prepareHashForDB (String plainPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hash = hashString(plainPassword);
        String encodedString = Base64.getEncoder().encodeToString(hash);
        return encodedString;
    }

    /**
     * Checks password validity
     * @param plainPassword - password from Web Form
     * @param encodedDbPassword - Base64 endoded password as it stored in DB
     * @return true when password is valid
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean isValidPassword (String plainPassword, String encodedDbPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hashedPassword = Base64.getDecoder().decode(encodedDbPassword);
        return isValidPassword (plainPassword, hashedPassword);
    }

    private byte[] hashString (String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return hash;
    }

    // TODO should be replaced by random generator
    private byte[] generateSalt() {
        byte[] salt = {
                (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00,
        };
        return salt;
        /*
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

         */
    }

    // TODO should be replaced by real salt extractor from password stored in DB
    private byte[] extractSalt (String dbPassword) {
        return generateSalt();
    }

    private boolean isValidPassword (String plainPassword, byte[] hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Arrays.equals (hashString(plainPassword), hashedPassword);
    }
}
