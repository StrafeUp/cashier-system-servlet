package cashiersystem.command.user;

import cashiersystem.command.Command;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.domain.User;
import cashiersystem.service.UserService;
import cashiersystem.util.PageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListAllUsersCommand implements Command {

    public static final int ITEMS_PER_PAGE = 10;
    private final UserService userService;

    public ListAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;
        String pageNumber = request.getParameter("page");

        if (pageNumber != null) {
            page = PageUtils.parsePageNumber(pageNumber, 1);
        }

        List<User> allUsersAtPage = userService.findAll(new Page(page, ITEMS_PER_PAGE));
        int userCount = userService.count();
        int pageCount = (int) Math.ceil(userCount * 1.0 / ITEMS_PER_PAGE);
        List<Integer> pages = IntStream.rangeClosed(1, pageCount).boxed().collect(Collectors.toList());

        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pages", pages);
        request.setAttribute("users", allUsersAtPage);
        return "/pages/users.jsp";
    }
}
