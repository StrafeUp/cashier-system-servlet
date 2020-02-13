package cashiersystem.controller;

import cashiersystem.command.Command;
import cashiersystem.command.user.LoginCommand;
import cashiersystem.command.user.LogoutCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

   /* private static final LoginCommand LOGIN_COMMAND = new LoginCommand();
    private static final LogoutCommand LOGOUT_COMMAND = new LogoutCommand();
    private Map<String, Command> userCommandNameToCommand;*/

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;


   /* @Before
    public void init() {
        userCommandNameToCommand = new HashMap<>();
        userCommandNameToCommand.put("/user/login", LOGIN_COMMAND);
        userCommandNameToCommand.put("/user/logout", LOGOUT_COMMAND);
    }*/

    @Test
    public void doGet() {
    }

    @Test
    public void doPost() {
    }
}