package cashiersystem.service.encoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger(PasswordEncoder.class);

    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private static final SecureRandom random = new SecureRandom();
    private static final int HASH_SIZE_BYTES = 16;
    private byte[] salt = "salt".getBytes();

    public String encode(String inputString) {
        KeySpec spec = new PBEKeySpec(inputString.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory;

        byte[] hash = new byte[0];

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.warn("Can't encrypt, encryptor not found", e);
        }
        return new String(hash);
    }
}
