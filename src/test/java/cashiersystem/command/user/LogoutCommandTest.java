package cashiersystem.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private LogoutCommand logoutCommand;

    @Test
    public void executeShouldInvalidateSession() {
        when(request.getSession()).thenReturn(httpSession);
        doNothing().when(httpSession).invalidate();

        logoutCommand.execute(request, response);

        verify(httpSession).invalidate();
        verifyZeroInteractions(response);
    }
}