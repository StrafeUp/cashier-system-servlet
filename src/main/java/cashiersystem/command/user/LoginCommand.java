package cashiersystem.command.user;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        if (email.isEmpty() || password.isEmpty()) {
            LOGGER.warn("email or password is absent");
            request.setAttribute("error", "Email or password can't be empty");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }

        Optional<User> user = userService.login(email, password);

        if (!user.isPresent()) {
            LOGGER.warn("Entity via email: " + email + " not found");
            request.setAttribute("error", "User not found, check your credentials");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }

        final HttpSession session = request.getSession(false);
        session.setAttribute("user", user.get());

        return "/user/listAllUsers";
    }
}
