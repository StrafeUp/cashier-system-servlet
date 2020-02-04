package cashiersystem.command.user;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class RegisterCommand implements Command {

    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String email = request.getParameter("email");
        final String password1 = request.getParameter("password1");
        final String password2 = request.getParameter("password2");
        final String username = request.getParameter("username");

        if (!Objects.equals(password1, password2)) {
            return "pages/register.jsp";
        }

        final User user = User.builder()
                .withEmail(email)
                .withUsername(username)
                .withPassword(password1)
                .build();

        userService.register(user);

        return "pages/login.jsp";
    }
}
