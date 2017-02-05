package ua.nure.poliakov.SummaryTask4.logic.common;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.email.SendEmail;
import ua.nure.poliakov.SummaryTask4.utils.encodind.Password;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for restore access by email.
 */

@WebServlet("/restoreAccess")
public class RestoreAccess extends HttpServlet {

    private static final Logger log = Logger.getLogger(RestoreAccess.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebPath.RESTORE_ACCESS_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = SendEmail.randomPassword();

        if (userDAO.getByLogin(login).getEmail().equals(email)) {
            try {
                SendEmail.sendEmail(userDAO.getByLogin(login).getEmail(),
                        SendEmail.restoreAccess(userDAO.getByLogin(login).getFullName(), password));
                log.debug("Message sent successfully to " + userDAO.getByLogin(login).getFullName());
                userDAO.updatePassword(login, Password.encodePassword(password));
                log.debug("Password was change");
                resp.sendRedirect("/signIn");
            } catch (MessagingException e) {
                log.error("Can't send restore message to " + userDAO.getByLogin(login).getFullName(), e);
                req.setAttribute(Session.RESTORE_INFO, "Can't send restore message");
                req.getRequestDispatcher(WebPath.RESTORE_ACCESS_PAGE).forward(req, resp);
            }
        } else if (!userDAO.isContainsLogin(login)) {
            log.debug("Login not exist");
            req.setAttribute(Session.RESTORE_INFO, "Login not exist");
            req.getRequestDispatcher(WebPath.RESTORE_ACCESS_PAGE).forward(req, resp);
        } else {
            log.debug("Wrong email");
            req.setAttribute(Session.RESTORE_INFO, "Wrong email");
            req.getRequestDispatcher(WebPath.RESTORE_ACCESS_PAGE).forward(req, resp);
        }
    }
}
