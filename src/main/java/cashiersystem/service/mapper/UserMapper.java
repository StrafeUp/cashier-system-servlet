package cashiersystem.service.mapper;

import cashiersystem.dao.domain.User;
import cashiersystem.entity.Role;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.encoder.PasswordEncoder;

public class UserMapper implements Mapper<UserEntity, User> {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity mapDomainToEntity(User domain) {
        return UserEntity.builder()
                .withId(domain.getId())
                .withUsername(domain.getUsername())
                .withEmail(domain.getEmail())
                .withRole(Role.CASHIER)
                .withPassword(passwordEncoder.encode(domain.getPassword()))
                .build();
    }

    @Override
    public User mapEntityToDomain(UserEntity entity) {
        return User.builder()
                .withId(entity.getId())
                .withUsername(entity.getUsername())
                .withEmail(entity.getEmail())
                .withRole(entity.getRole())
                .withPassword(entity.getPassword())
                .build();
    }
}
