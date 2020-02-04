package cashiersystem.controller;

import cashiersystem.command.Command;
import cashiersystem.injector.ApplicationInjector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserController extends HttpServlet {
    private final Map<String, Command> userCommandToCommand;

    public UserController() {
        ApplicationInjector applicationInjector = ApplicationInjector.getInstance();
        this.userCommandToCommand = applicationInjector.getUserCommandNameToCommand();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getRequestURI();
        String forwardUrl = this.userCommandToCommand.get(command).execute(req, resp);
        req.getRequestDispatcher(forwardUrl).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getRequestURI();
        String forwardUrl = this.userCommandToCommand.get(command).execute(req, resp);
        resp.sendRedirect(forwardUrl);
    }
}
