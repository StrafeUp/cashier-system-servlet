package cashiersystem.command.user;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListAllUsersCommand implements Command {
    public static final int ITEMS_PER_PAGE = 10;
    private static final Logger LOGGER = LogManager.getLogger(ListAllUsersCommand.class);
    private final UserService userService;

    public ListAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                LOGGER.warn("Tried to access page with string letters");
            }
        }

        List<User> allUsersAtPage = userService.findAll(new Page(page, ITEMS_PER_PAGE));
        request.setAttribute("users", allUsersAtPage);
        return "/pages/users.jsp";
    }
}
