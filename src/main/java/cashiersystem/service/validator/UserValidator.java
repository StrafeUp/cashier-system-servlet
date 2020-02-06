package cashiersystem.service.validator;

import cashiersystem.dao.domain.User;
import cashiersystem.service.validator.exception.InvalidFieldException;

import java.util.function.Function;
import java.util.regex.Pattern;

public class UserValidator implements Validator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^([a-zA-Z0-9]){8,20}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");

    @Override
    public void validate(User user) {
        validateEmail(user);
        validateUsername(user);
        validatePassword(user);
    }

    private void validateEmail(User user) {
        validateString(EMAIL_PATTERN, user, User::getEmail, new InvalidFieldException("Email is incorrect", "Email"));
    }

    private void validatePassword(User user) {
        validateString(PASSWORD_PATTERN, user, User::getPassword, new InvalidFieldException("Password is incorrect", "Password"));
    }

    private void validateUsername(User user) {
        validateString(USERNAME_PATTERN, user, User::getUsername, new InvalidFieldException("Username is incorrect", "Username"));
    }

    private void validateString(Pattern pattern, User user, Function<User, String> function, InvalidFieldException exception) {
        if (!pattern.matcher(function.apply(user)).matches()) {
            throw exception;
        }
    }
}
