package ua.nure.poliakov.SummaryTask4.logic.admin.users;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.user.UsersList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obtain user list.
 */

@WebServlet("/userList")
public class UserList extends HttpServlet {

    private static final Logger log = Logger.getLogger(UserList.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN);
        req.getSession().setAttribute("userList", userDAO.getAllUsersByRole("user", login));
        resp.setIntHeader("Refresh", 200);
        log.debug("UserList page: " + login);
        req.getRequestDispatcher(WebPath.USER_LIST_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersList.getUsers(req, resp);
    }
}
