package ua.nure.poliakov.SummaryTask4.logic.admin.users;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for editing user status (ban/unban).
 */

@WebServlet("/changeStatus")
public class EditStatus extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditStatus.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        log.debug("EditStatus page: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        if (userDAO.getByLogin(login).getBan().equals(true)) {
            userDAO.banUser(login, false);
            log.debug("Status was change ==> " + login);
            resp.sendRedirect("/userList");
        } else {
            userDAO.banUser(login, true);
            log.debug("Status was change ==> " + login);
            resp.sendRedirect("/userList");
        }
    }
}