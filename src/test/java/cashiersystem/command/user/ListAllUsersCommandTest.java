package cashiersystem.command.user;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ListAllUsersCommandTest {

    public static final List<User> USERS = Collections.singletonList(User.builder().build());
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService userService;

    @InjectMocks
    private ListAllUsersCommand listAllUsersCommand;

    @Test
    public void executeShouldCallSetAttribute3Times() {
        when(request.getParameter("page")).thenReturn("1");
        when(userService.findAll(any(Page.class))).thenReturn(USERS);
        when(userService.count()).thenReturn(10);
        doNothing().when(request).setAttribute(any(),any());
        listAllUsersCommand.execute(request,response);
        verify(request, times(3)).setAttribute(any(), any());
    }
}