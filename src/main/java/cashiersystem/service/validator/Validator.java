package cashiersystem.service.validator;

import cashiersystem.dao.domain.User;

public interface Validator {
    void validate(User entity);
}
