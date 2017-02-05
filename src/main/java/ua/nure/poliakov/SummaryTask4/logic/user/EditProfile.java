package ua.nure.poliakov.SummaryTask4.logic.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.entity.User;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.encodind.Password;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for editing user profile.
 */

@WebServlet("/editProfile")
public class EditProfile extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditProfile.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();
    private Validator<User> validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("EditProfile: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        req.getRequestDispatcher(WebPath.EDIT_PROFILE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String login = String.valueOf(session.getAttribute(Session.AUTHENTICATED_LOGIN));
        Boolean notification = Boolean.valueOf(req.getParameter("notification"));
        validator = new ValidateUser();

        try {
            if (validator.validate(new User(fullName, login, email, password))) {
                userDAO.updateUser(new User(fullName, login, email, Password.encodePassword(password)));
                userDAO.updateSettings(login, notification);
                log.debug(login + " profile was updated");
                session.setAttribute(Session.AUTHENTICATED_FULL_NAME, userDAO.getByLogin(login).getFullName());
                session.setAttribute(Session.AUTHENTICATED_EMAIL, userDAO.getByLogin(login).getEmail());
                session.setAttribute(Session.NOTIFICATION, userDAO.getSettings(login));
                resp.sendRedirect("/userCabinet");
            }
        } catch (ValidationException e) {
            log.error("No valid data", e);
            req.setAttribute("editInfo", "You try enter incorrect data");
            req.getRequestDispatcher(WebPath.EDIT_PROFILE_PAGE).forward(req, resp);
        }
    }
}
