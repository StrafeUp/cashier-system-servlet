package cashiersystem.dao;

import cashiersystem.entity.UserEntity;

import java.util.Optional;

public interface UserCrudDao extends CrudPageableDao<UserEntity> {

    Optional<UserEntity> findByEmail(String email);
}
