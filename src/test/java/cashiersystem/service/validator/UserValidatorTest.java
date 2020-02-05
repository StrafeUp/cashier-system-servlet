package cashiersystem.service.validator;

import cashiersystem.dao.domain.User;
import cashiersystem.service.validator.exception.InvalidFieldException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validateShouldThrowInvalidFieldExceptionWithoutPassword() {
        expectedException.expect(InvalidFieldException.class);
        expectedException.expectMessage("Password is incorrect");
        User exampleUser = User.builder()
                .withEmail("email@gmail.com")
                .withUsername("Example")
                .withPassword("").build();
        userValidator.validate(exampleUser);
    }

    @Test
    public void validateShouldThrowInvalidFieldExceptionWithoutUsername() {
        expectedException.expect(InvalidFieldException.class);
        expectedException.expectMessage("Username is incorrect");
        User exampleUser = User.builder()
                .withEmail("email@gmail.com")
                .withUsername("")
                .withPassword("1234567890").build();
        userValidator.validate(exampleUser);
    }

    @Test
    public void validateShouldThrowInvalidFieldExceptionWithoutEmail() {
        expectedException.expect(InvalidFieldException.class);
        expectedException.expectMessage("Email is incorrect");
        User exampleUser = User.builder()
                .withEmail("")
                .withUsername("Example")
                .withPassword("1234567890").build();
        userValidator.validate(exampleUser);
    }

    @Test
    public void validateShouldProceedWithoutException() {
        User exampleUser = User.builder()
                .withEmail("email@gmail.com")
                .withUsername("Example")
                .withPassword("1234567890").build();
        userValidator.validate(exampleUser);
    }
}