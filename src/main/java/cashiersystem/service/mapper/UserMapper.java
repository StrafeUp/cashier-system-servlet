package cashiersystem.service.mapper;

import cashiersystem.dao.domain.User;
import cashiersystem.entity.Role;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.encoder.PasswordEncoder;

public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .withId(userEntity.getId())
                .withUsername(userEntity.getUsername())
                .withEmail(userEntity.getEmail())
                .withRole(userEntity.getRole())
                .withPassword(userEntity.getPassword())
                .build();
    }

    public UserEntity mapUserToUserEntity(User user) {
        return UserEntity.builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withRole(Role.CASHIER)
                .withPassword(passwordEncoder.encode(user.getPassword()))
                .build();
    }
}
