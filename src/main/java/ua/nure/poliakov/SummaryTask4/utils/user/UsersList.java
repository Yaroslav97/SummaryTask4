package ua.nure.poliakov.SummaryTask4.utils.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obtain all list of users for admin by role or by search result.
 */

public class UsersList {

    private static final Logger log = Logger.getLogger(UsersList.class);
    private static UserDAO userDAO = UserDAOImplement.getInstance();

    public static void getUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN);
        String role = req.getParameter("role");
        String search = req.getParameter("search");
        if (role != null) {
            switch (role) {
                case "users":
                    log.debug(login + " obtains user list");
                    req.getSession().setAttribute(Session.USER_LIST, userDAO.getAllUsersByRole("user", login));
                    req.getRequestDispatcher(WebPath.USER_LIST_PAGE).forward(req, resp);
                    break;
                case "admins":
                    log.debug(login + " obtains admin list");
                    req.getSession().setAttribute(Session.USER_LIST, userDAO.getAllUsersByRole("admin", login));
                    req.getRequestDispatcher(WebPath.USER_LIST_PAGE).forward(req, resp);
                    break;
                default:
                    req.getRequestDispatcher(WebPath.USER_LIST_PAGE).forward(req, resp);
            }
        } else {
            if (search != null) {
                log.debug("Obtain user list by name: " + search);
                req.getSession().setAttribute(Session.USER_LIST, userDAO.searchByName(search));
                req.getRequestDispatcher(WebPath.USER_LIST_PAGE).forward(req, resp);
            }
        }
    }
}
