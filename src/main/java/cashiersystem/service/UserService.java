package cashiersystem.service;

import cashiersystem.dao.domain.User;

import java.util.Optional;

public interface UserService extends PageableService<User> {

    void register(User user);

    Optional<User> login(String email, String password);

}