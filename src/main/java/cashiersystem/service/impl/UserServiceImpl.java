package cashiersystem.service.impl;

import cashiersystem.dao.UserCrudDao;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.UserService;
import cashiersystem.service.encoder.PasswordEncoder;
import cashiersystem.service.exception.EntityAlreadyExistsException;
import cashiersystem.service.mapper.UserMapper;
import cashiersystem.service.validator.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserCrudDao userPageableCrudDao;
    private final UserMapper userMapper;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserCrudDao userPageableCrudDao, UserMapper userMapper, Validator validator, PasswordEncoder passwordEncoder) {
        this.userPageableCrudDao = userPageableCrudDao;
        this.userMapper = userMapper;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user) {
        validator.validate(user);
        Optional<UserEntity> byEmail = userPageableCrudDao.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new EntityAlreadyExistsException(401);
        }
        userPageableCrudDao.save(userMapper.mapUserToUserEntity(user));
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userPageableCrudDao.findByEmail(email)
                .map(userMapper::mapUserEntityToUser)
                .filter(x -> Objects.equals(x.getPassword(), passwordEncoder.encode(password)));
    }

    public List<User> findAll(Page page) {
        int maxPageNumber = (int) (userPageableCrudDao.count() / page.getItemsPerPage());
        int pageNumber = page.getPageNumber();

        if (maxPageNumber <= 0) {
            maxPageNumber = 1;
        }
        if (pageNumber <= 0) {
            pageNumber = 1;

        } else if (pageNumber >= maxPageNumber) {
            pageNumber = maxPageNumber;
        }
        return userPageableCrudDao.findAll(new Page(pageNumber, page.getItemsPerPage())).stream()
                .map(userMapper::mapUserEntityToUser)
                .collect(Collectors.toList());
    }
}
