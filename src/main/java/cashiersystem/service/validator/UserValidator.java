package cashiersystem.service.validator;

import cashiersystem.dao.domain.User;
import cashiersystem.service.exception.InvalidEmailException;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserValidator implements Validator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^([a-zA-Z0-9]){8,20}$");

    @Override
    public void validate(User entity) {
        Optional.ofNullable(entity)
                .map(User::getEmail)
                .filter(x -> EMAIL_PATTERN.matcher(x).matches())
                .orElseThrow(InvalidEmailException::new);
    }


}
