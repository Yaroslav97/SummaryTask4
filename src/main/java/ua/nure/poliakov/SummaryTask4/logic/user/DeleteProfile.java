package ua.nure.poliakov.SummaryTask4.logic.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for removal user profile.
 */

@WebServlet("/deleteUser")
public class DeleteProfile extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeleteProfile.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO.deleteUser(req.getParameter("login"));
        log.debug(req.getParameter("login") + " removed his account");
        req.getSession().invalidate();
        resp.sendRedirect("/index");
    }
}
