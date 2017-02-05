package ua.nure.poliakov.SummaryTask4.logic.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Unsubscribes from edition.
 */

@WebServlet("/unsubscribe")
public class Unsubscribe extends HttpServlet {

    private static final Logger log = Logger.getLogger(Unsubscribe.class);
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = String.valueOf(req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        Integer idEdition = Integer.valueOf(req.getParameter("id"));
        log.debug("Unsubscribe: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        if (editionDAO.isSubscribe(login, idEdition)) {
            editionDAO.unsubscribe(login, idEdition);
            req.getSession().setAttribute("subscribesList", editionDAO.getAllSubscriptions(login));
            log.debug(login + " unsubscribes " + editionDAO.getEdition(idEdition).getName());
            resp.sendRedirect("/userCabinet");
        } else {
            log.debug(login + " does not subscribes on " + editionDAO.getEdition(idEdition).getName() + " or edition not exist");
            resp.sendRedirect("/userCabinet");
        }
    }
}
