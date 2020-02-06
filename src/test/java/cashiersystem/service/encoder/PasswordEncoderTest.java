package cashiersystem.service.encoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordEncoderTest {

    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "\u001B4]�_b�Z�ے)�z�[";
    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Test
    public void shouldEncryptString() {
        String encodedPassword = passwordEncoder.encode(PASSWORD);
        assertEquals(ENCODED_PASSWORD, encodedPassword);
    }

    @Test
    public void encryptedStringsShouldMatch() {
        String encodedPassword1 = passwordEncoder.encode(PASSWORD);
        String encodedPassword2 = passwordEncoder.encode(PASSWORD);
        assertEquals(encodedPassword1, encodedPassword2);
    }
}