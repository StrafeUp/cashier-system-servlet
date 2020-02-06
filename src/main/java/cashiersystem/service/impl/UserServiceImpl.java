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

    private UserCrudDao userCrudDao;
    private UserMapper userMapper;
    private Validator validator;
    private PasswordEncoder passwordEncoder;

    //TODO validation
    public UserServiceImpl(UserCrudDao userCrudDao, UserMapper userMapper, Validator validator, PasswordEncoder passwordEncoder) {
        this.userCrudDao = userCrudDao;
        this.userMapper = userMapper;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user) {
        validator.validate(user);
        Optional<UserEntity> byEmail = userCrudDao.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new EntityAlreadyExistsException(401);
        }
        userCrudDao.save(userMapper.mapDomainToEntity(user));
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userCrudDao.findByEmail(email)
                .map(userMapper::mapEntityToDomain)
                .filter(x -> Objects.equals(x.getPassword(), passwordEncoder.encode(password)));
    }

    public List<User> findAll(Page page) {
        int maxPageNumber = (int) Math.ceil(count() * 1.0 / page.getItemsPerPage());
        int pageNumber = page.getPageNumber();

        if (maxPageNumber <= 0) {
            maxPageNumber = 1;
        }
        if (pageNumber <= 0) {
            pageNumber = 1;

        } else if (pageNumber >= maxPageNumber) {
            pageNumber = maxPageNumber;
        }
        return userCrudDao.findAll(new Page(pageNumber, page.getItemsPerPage())).stream()
                .map(userMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) userCrudDao.count();
    }
}
