package name.erzin.learn.hl.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import jakarta.servlet.http.HttpServletRequest;
import name.erzin.learn.hl.entity.Account;
import name.erzin.learn.hl.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// NOTE: salt is hardcoded and not stored in the DB
@Component
public class SecurityProvider {
    static int JWT_EXPIRATION_SEC = 60 * 60 * 24; // 1 day
    AccountRepo accountRepo;

    private final SecretKeyFactory secretKeyFactory;

    public SecurityProvider(@Autowired AccountRepo accountRepo) throws NoSuchAlgorithmException {
        this.secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        this.accountRepo = accountRepo;
    }

    /**
     * Prepares password for storing in the DB
     *
     * @param plainPassword - password from Web Form
     * @return Password hash, encoded as Base64, ready to store into DB
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public String prepareHashForDB(String plainPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hash = hashString(plainPassword);
        String encodedString = Base64.getEncoder().encodeToString(hash);
        return encodedString;
    }

    public boolean isValidUserPassword(String login, String plainPassword) {
        String encodedDbPassword = getEncodedUserPassword(login);

        try {
            return isValidPassword(plainPassword, encodedDbPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }
    }

    private String getEncodedUserPassword(String login) {
        ArrayList<Account> result = accountRepo.findByLogin(login);
        try {
            return result.get(0).getPassword();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks password validity
     *
     * @param plainPassword     - password from Web Form
     * @param encodedDbPassword - Base64 endoded password as it stored in DB
     * @return true when password is valid
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean isValidPassword(String plainPassword, String encodedDbPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (encodedDbPassword == null) {
            return false;
        }
        byte[] hashedPassword = Base64.getDecoder().decode(encodedDbPassword);
        return isValidPassword(plainPassword, hashedPassword);
    }

    private boolean isValidPassword(String plainPassword, byte[] hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Arrays.equals(hashString(plainPassword), hashedPassword);
    }

    private byte[] hashString(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
        return hash;
    }

    // TODO should be replaced by random generator
    private byte[] generateSalt() {
        byte[] salt = {
                (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        };
        return salt;
        /*
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

         */
    }

    // TODO should be replaced by real salt extractor from password stored in DB
    private byte[] extractSalt(String dbPassword) {
        return generateSalt();
    }

    public boolean isValidJwt(String jwtStr) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseSignedClaims(jwtStr);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public String extractJwtFromHeader(String authHeader) {
        return authHeader.substring(7);
    }

    public String extractLoginFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return extractLoginFromHeader(authHeader);
    }

    public String extractLoginFromHeader(String authHeader) {
        return extractLoginFromJwt(extractJwtFromHeader(authHeader));
    }

    public String extractLoginFromJwt(String jwtStr) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseSignedClaims(jwtStr);
            return (String) claims.getPayload().get("login");
        } catch (JwtException e) {
            return null;
        }
    }

    public String createJwt(String login) {
        String jwt = Jwts.builder()
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(getExpirationSec())))
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .claim("login", login)
                .compact();
        return jwt;
    }

    private int getExpirationSec() {
        return JWT_EXPIRATION_SEC;
    }

    private byte[] getSigningKey() {
        return TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");
    }
}
