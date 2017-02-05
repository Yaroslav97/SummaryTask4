package ua.nure.poliakov.SummaryTask4.logic.common.registration;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.entity.User;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.email.SendEmail;
import ua.nure.poliakov.SummaryTask4.utils.encodind.Password;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for registration new user.
 */

@WebServlet("/registration")
public class Registration extends HttpServlet {

    private static final Logger log = Logger.getLogger(Registration.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();
    private Validator<User> validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebPath.REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String password = req.getParameter("password");
        validator = new ValidateUser();

        try {
            if (validator.validate(new User(fullName, login, email, password)) && !userDAO.isContainsLogin(login)) {
                if (role.equals("admin")) {
                    userDAO.addUser(new User(fullName, login, email, .0, role, true, Password.encodePassword(password)));
                    log.debug("Added new admin: " + userDAO.getByLogin(login).getFullName());
                    resp.sendRedirect("/index");
                } else if (role.equals("user")) {
                    userDAO.addUser(new User(fullName, login, email, .0, role, true, Password.encodePassword(password)));
                    log.debug("Added new user: " + userDAO.getByLogin(login).getFullName());
                    try {
                        SendEmail.sendEmail(email, SendEmail.registrationEmail(login, email));
                        log.debug("Send registration email to " + fullName);
                    } catch (MessagingException e) {
                        log.error("Cannot to send registration message to: " + userDAO.getByLogin(login).getFullName(), e);
                    }
                    resp.sendRedirect("/index");
                }
            } else if (userDAO.isContainsLogin(login)) {
                req.setAttribute(Session.REG_INFO, "This login already exist");
                log.debug("This login already exist");
                req.getRequestDispatcher(WebPath.REGISTRATION_PAGE).forward(req, resp);
            }
        } catch (ValidationException e) {
            log.error("No valid data", e);
            req.setAttribute(Session.REG_INFO, "You try enter incorrect data");
            req.getRequestDispatcher(WebPath.REGISTRATION_PAGE).forward(req, resp);
        }
    }
}
