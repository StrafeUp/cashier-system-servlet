package cashiersystem.dao;

import cashiersystem.entity.User;

import java.util.Optional;

public interface UserCrudDao extends CrudPageableDao<User> {
    Optional<User> findByEmail(String email);
}
