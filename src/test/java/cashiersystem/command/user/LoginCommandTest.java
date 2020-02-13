package cashiersystem.command.user;

import cashiersystem.dao.domain.User;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.UserService;
import cashiersystem.service.mapper.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginCommandTest {

    public static final String EMAIL = "email@gmail.com";
    public static final String PASSWORD = "secret";
    private static final Optional<User> OPTIONAL_USER = Optional.of(User.builder().build());
    private static final User USER = User.builder().build();
    private static final UserEntity USER_ENTITY = UserEntity.builder().build();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;

    @Mock
    private UserService userService;
    @Mock
    private Mapper<UserEntity, User> userMapper;

    @InjectMocks
    private LoginCommand loginCommand;

    @Test
    public void executeShouldLoginUser() throws Exception {
        when(request.getParameter("email")).thenReturn(EMAIL);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        doNothing().when(httpSession).setAttribute(any(), any());
        when(request.getSession(false)).thenReturn(httpSession);
        when(userMapper.mapEntityToDomain(any())).thenReturn(USER);

        when(userService.login(EMAIL, PASSWORD)).thenReturn(OPTIONAL_USER);
        loginCommand.execute(request, response);

        verify(request, times(2)).getParameter(any());
    }
}