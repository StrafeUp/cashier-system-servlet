package cashiersystem.command.user;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;
import cashiersystem.service.exception.EntityAlreadyExistsException;
import cashiersystem.service.validator.exception.InvalidFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RegisterCommand implements Command {

    public static final String REGISTER_PAGE_PATH = "/pages/register.jsp";
    public static final String ERROR_PAGE_ATTRIBUTE = "error";

    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String email = request.getParameter("email");
        final String password1 = request.getParameter("password1");
        final String password2 = request.getParameter("password2");
        final String username = request.getParameter("username");

        if(email.isEmpty() || username.isEmpty() || password1.isEmpty() || password2.isEmpty()){
            LOGGER.warn("Field cannot by empty");
            request.setAttribute(ERROR_PAGE_ATTRIBUTE, "Field cannot be empty");
            request.getRequestDispatcher(REGISTER_PAGE_PATH).forward(request, response);
        }

        if (!Objects.equals(password1, password2)) {
            LOGGER.warn("Passwords do not match");
            request.setAttribute(ERROR_PAGE_ATTRIBUTE, "Passwords do not match");
            request.getRequestDispatcher(REGISTER_PAGE_PATH).forward(request, response);
        }

        final User user = User.builder()
                .withEmail(email)
                .withUsername(username)
                .withPassword(password1)
                .build();

        try {
            userService.register(user);
        } catch (EntityAlreadyExistsException e) {
            request.setAttribute(ERROR_PAGE_ATTRIBUTE, "User already exists");
            request.getRequestDispatcher(REGISTER_PAGE_PATH).forward(request, response);
        } catch (InvalidFieldException e) {
            request.setAttribute(ERROR_PAGE_ATTRIBUTE, e.getField() + " is incorrect");
            request.getRequestDispatcher(REGISTER_PAGE_PATH).forward(request, response);
        }

        return "/pages/login.jsp";
    }
}
