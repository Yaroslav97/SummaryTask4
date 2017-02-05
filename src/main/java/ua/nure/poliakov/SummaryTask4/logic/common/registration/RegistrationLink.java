package ua.nure.poliakov.SummaryTask4.logic.common.registration;

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
 * Link for registration email.
 */

@WebServlet("/link")
public class RegistrationLink extends HttpServlet {

    private static final Logger log = Logger.getLogger(RegistrationLink.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userDAO.isContainsLogin(req.getParameter("login")) &&
                userDAO.getByLogin(req.getParameter("login")).getEmail().equals(req.getParameter("email"))) {
            userDAO.banUser(req.getParameter("login"), false);
            log.debug("Confirmation email");
            resp.sendRedirect("/signIn");
        } else {
            log.debug("Email is not confirmation");
            resp.sendRedirect("/index");
        }
    }
}
