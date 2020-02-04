package cashiersystem.command.user;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final User user = userService.login(email, password);

        final HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return "pages/users.jsp";
    }
}
