package ua.nure.poliakov.SummaryTask4.logic.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.email.SendEmail;
import ua.nure.poliakov.SummaryTask4.utils.pay.Pay;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Subscribe on edition.
 */

@WebServlet("/subscribe")
public class Subscribe extends HttpServlet {

    private static final Logger log = Logger.getLogger(Subscribe.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String login = String.valueOf(req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        log.debug("Subscribe page");

        if (editionDAO.isContains(id) && !login.equals("null") && !editionDAO.isSubscribe(login, id) && Pay.isCanPay(login, id)) {
            editionDAO.subscribe(login, id);
            req.getSession().setAttribute(Session.AUTHENTICATED_SCORE, userDAO.getByLogin(login).getScore());
            log.debug(login + " subscribe on " + editionDAO.getEdition(id).getName() + "[" + id + "]");
            req.setAttribute(Session.SUBSCRIBE_INFO, "You subscribe on " + editionDAO.getEdition(id).getName());
            if (userDAO.getSettings(login)) {
                try {
                    SendEmail.sendEmail(userDAO.getByLogin(login).getEmail(), SendEmail.subscribeEmail(login, id));
                } catch (MessagingException e) {
                    log.error("Cannot send email to " + login, e);
                }
            }
            resp.sendRedirect("/index");
        } else if (editionDAO.isSubscribe(login, id)) {
            log.debug(login + " already subscribes on this edition");
            req.setAttribute(Session.SUBSCRIBE_INFO, "You already subscribe for " + editionDAO.getEdition(id).getName());
            req.getRequestDispatcher(WebPath.INDEX_PAGE).forward(req, resp);
        } else if (!login.equals("null") && !Pay.isCanPay(login, id)) {
            log.debug(login + " cannot pay for subscribe ==> " + editionDAO.getEdition(id).getName());
            req.setAttribute(Session.SUBSCRIBE_INFO, "You have not required balance");
            req.getRequestDispatcher(WebPath.INDEX_PAGE).forward(req, resp);
        } else if (!editionDAO.isContains(id)) {
            log.debug(id + " ==> not exist edition");
            req.setAttribute(Session.SUBSCRIBE_INFO, "Wrong id edition");
            req.getRequestDispatcher(WebPath.INDEX_PAGE).forward(req, resp);
        } else if (login.equals("null")) {
            log.debug("User is not authenticated");
            resp.sendRedirect("/signIn");
        }
    }
}
