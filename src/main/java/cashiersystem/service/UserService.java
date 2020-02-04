package cashiersystem.service;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;

import java.util.List;

public interface UserService {

    void register(User user);

    User login(String email, String password);

    List<User> findAll(Page page);

}