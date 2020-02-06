package cashiersystem.service.mapper;

import cashiersystem.dao.domain.User;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.encoder.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final String PASSWORD = "password";

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private Mapper<UserEntity, User> mapper = new UserMapper(passwordEncoder);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void mapUserToUserEntity() {
        User user = User.builder()
                .withEmail("test@gmail.com")
                .withUsername("Example")
                .withPassword(PASSWORD).build();

        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        UserEntity userEntity = mapper.mapDomainToEntity(user);

        assertEquals(ENCODED_PASSWORD, userEntity.getPassword());
    }

    @Test
    public void mapUserEntityToUser() {
        UserEntity exampleUser = UserEntity.builder()
                .withId(1L)
                .withEmail("test@gmail.com")
                .withUsername("Example")
                .withPassword(PASSWORD).build();
        User user = mapper.mapEntityToDomain(exampleUser);
        assertEquals("test@gmail.com", user.getEmail());
    }
}