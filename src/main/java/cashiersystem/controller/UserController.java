package cashiersystem.controller;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;
import cashiersystem.injector.ApplicationInjector;
import cashiersystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserController extends HttpServlet {
    private final UserService userService;
    private final Map<String, Command> userCommandToCommand;

    public UserController() {
        ApplicationInjector applicationInjector = ApplicationInjector.getInstance();
        this.userCommandToCommand = applicationInjector.getUserCommandNameToCommand();
        this.userService = applicationInjector.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int itemsPerPage = 1;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        if (req.getParameter("perPage") != null) {
            itemsPerPage = Integer.parseInt(req.getParameter("perPage"));
        }

        List<User> allUsersAtPage = userService.findAll(new Page(page, itemsPerPage));
        req.setAttribute("users", allUsersAtPage);

        req.getRequestDispatcher("pages/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        this.userCommandToCommand.get(command).execute(req, resp);

        req.getRequestDispatcher("pages/users.jsp").forward(req, resp);
    }
}
