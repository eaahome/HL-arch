package name.erzin.learn.hl.service;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static org.junit.Assert.*;

public class SecurityProviderTest {
    SecurityProvider sp = new SecurityProvider();

    public SecurityProviderTest() throws NoSuchAlgorithmException {
    }

    static String ENCODED_PASSWORD = "8DUJw3vDAUsBQnBYFwmnmg==";
    static String PLAIN_PASSWORD = "123";

    @Test
    public void prepareHashForDBTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        assertEquals(ENCODED_PASSWORD, sp.prepareHashForDB(PLAIN_PASSWORD));
    }

    @Test
    public void isValidPasswordTest () throws NoSuchAlgorithmException, InvalidKeySpecException {
        assertTrue (sp.isValidPassword(PLAIN_PASSWORD, ENCODED_PASSWORD));
    }
}
