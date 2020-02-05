package cashiersystem.service;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void register(User user);

    Optional<User> login(String email, String password);

    List<User> findAll(Page page);

    int count();

}