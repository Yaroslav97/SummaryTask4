package ua.nure.poliakov.SummaryTask4.logic.common;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.encodind.Password;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SignIn user in personal account.
 */

@WebServlet("/signIn")
public class SignIn extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignIn.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("SignIn");
        req.getRequestDispatcher(WebPath.LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (userDAO.isContainsLogin(login) && userDAO.getByLogin(login).getPassword().
                equals(Password.encodePassword(password)) && !userDAO.getByLogin(login).getBan()) {
            session.setAttribute(Session.AUTHENTICATED_LOGIN, userDAO.getByLogin(login).getLogin());
            session.setAttribute(Session.AUTHENTICATED_FULL_NAME, userDAO.getByLogin(login).getFullName());
            session.setAttribute(Session.AUTHENTICATED_EMAIL, userDAO.getByLogin(login).getEmail());
            session.setAttribute(Session.AUTHENTICATED_ROLE, userDAO.getByLogin(login).getRole());
            session.setAttribute(Session.AUTHENTICATED_BAN, userDAO.getByLogin(login).getBan());
            session.setAttribute(Session.AUTHENTICATED_SCORE, userDAO.getScore(login));
            session.setAttribute(Session.NOTIFICATION, userDAO.getSettings(login));
            log.debug("sign in ==> " + login);
            resp.sendRedirect("/index");
        } else if (!userDAO.isContainsLogin(login)) {
            log.debug("Incorrect login or login not exist");
            req.getRequestDispatcher(WebPath.LOGIN_ERROR_PAGE).forward(req, resp);
        } else if (!userDAO.getByLogin(login).getPassword().equals(Password.encodePassword(password))) {
            req.setAttribute(Session.SIGN_IN_INFO, "Wrong password");
            log.debug("Wrong password ==> " + login);
            req.getRequestDispatcher(WebPath.LOGIN_PAGE).forward(req, resp);
        } else if (userDAO.getByLogin(login).getBan()) {
            log.debug("Access denied ==> " + login);
            req.getRequestDispatcher(WebPath.ACCESS_DENIED_PAGE).forward(req, resp);

        }
    }
}
