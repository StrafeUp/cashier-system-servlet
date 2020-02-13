package cashiersystem.command.user;

import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegisterCommandTest {

    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    private static final User USER = User.builder()
            .withUsername(USERNAME)
            .withEmail(EMAIL)
            .withPassword(PASSWORD)
            .build();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;
    @Mock
    private UserService userService;

    @InjectMocks
    private RegisterCommand registerCommand;

    @Test
    public void execute() throws Exception {
        when(request.getParameter("email")).thenReturn(EMAIL);
        when(request.getParameter("password1")).thenReturn(PASSWORD);
        when(request.getParameter("password2")).thenReturn(PASSWORD);
        when(request.getParameter("username")).thenReturn(USERNAME);
        doNothing().when(userService).register(USER);

        registerCommand.execute(request, response);

        verifyZeroInteractions(httpSession);
        verifyZeroInteractions(response);
        verify(request, times(4)).getParameter(anyString());
    }
}