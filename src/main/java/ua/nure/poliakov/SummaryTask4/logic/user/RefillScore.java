package ua.nure.poliakov.SummaryTask4.logic.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.entity.Score;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.encodind.Password;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.UserValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for refill user score.
 */

@WebServlet("/score")
public class RefillScore extends HttpServlet {

    private static final Logger log = Logger.getLogger(RefillScore.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();
    private UserValidate<String, Double> userValidate;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebPath.REFILL_SCORE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Double score = Double.valueOf(req.getParameter("score"));
        String password = req.getParameter("password");
        String login = String.valueOf(req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        userValidate = new ValidateUser();

        try {
            if (userValidate.score(score) && userDAO.getByLogin(login).getPassword().equals(Password.encodePassword(password))) {
                userDAO.updateScore(new Score(login, score, "refill"));
                req.getSession().setAttribute(Session.AUTHENTICATED_SCORE, userDAO.getByLogin(login).getScore());
                log.debug("Refill score ==> " + login);
                resp.sendRedirect("/userCabinet");
            } else if (!userDAO.getByLogin(login).getPassword().equals(Password.encodePassword(password))) {
                log.debug("Wrong password ==> " + login);
                req.setAttribute("scoreInfo", "Wrong password");
                req.getRequestDispatcher(WebPath.REFILL_SCORE_PAGE).forward(req, resp);
            }
        } catch (ValidationException e) {
            log.error("No valid data", e);
            req.setAttribute("scoreInfo", "Not valid data");
            req.getRequestDispatcher(WebPath.REFILL_SCORE_PAGE).forward(req, resp);
        }
    }
}
