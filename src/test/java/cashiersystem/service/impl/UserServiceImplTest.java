package cashiersystem.service.impl;

import cashiersystem.dao.UserCrudDao;
import cashiersystem.dao.domain.User;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.encoder.PasswordEncoder;
import cashiersystem.service.mapper.UserMapper;
import cashiersystem.service.validator.Validator;
import cashiersystem.service.validator.exception.InvalidFieldException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email@gmail.com";
    private static final String INCORRECT_PASSWORD = "incorrect_password";
    private static final String ENCODED_INCORRECT_PASSWORD = "encode_incorrect_password";
    private static final User USER =
            User.builder()
                    .withUsername(USERNAME)
                    .withEmail(EMAIL)
                    .withPassword(ENCODED_PASSWORD)
                    .build();
    private static final UserEntity USER_ENTITY =
            UserEntity.builder()
                    .withUsername(USERNAME)
                    .withEmail(EMAIL)
                    .withPassword(ENCODED_PASSWORD)
                    .build();

    @Mock
    private UserCrudDao userCrudDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private Validator validator;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void resetMocks() {
        reset(userCrudDao, passwordEncoder, validator);
    }


    @Test
    public void loginShouldReturnValidUser() {
        when(userCrudDao.findByEmail(anyString())).thenReturn(Optional.of(USER_ENTITY));
        when(userMapper.mapEntityToDomain(any(UserEntity.class))).thenReturn(USER);
        when(passwordEncoder.encode(eq(PASSWORD))).thenReturn(ENCODED_PASSWORD);

        userService.login(EMAIL, PASSWORD);

        verify(passwordEncoder).encode(eq(PASSWORD));
        verify(userCrudDao).findByEmail(eq(EMAIL));
    }

    @Test
    public void registerShouldSucceed() {
        doNothing().when(validator).validate(any(User.class));
        when(userCrudDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.mapDomainToEntity(any(User.class))).thenReturn(USER_ENTITY);

        userService.register(USER);

        verify(userCrudDao).findByEmail(eq(EMAIL));
        verify(validator).validate(any(User.class));
    }

    @Test(expected = InvalidFieldException.class)
    public void registerShouldThrowInvalidFieldException() {
        doThrow(new InvalidFieldException("")).when(validator).validate(USER);
        userService.register(USER);
    }

    @Test
    public void countShouldReturnNumber() {
        when(userCrudDao.count()).thenReturn(1L);
        userService.count();
        verify(userCrudDao, times(1)).count();
    }
}