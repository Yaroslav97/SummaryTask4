package ua.nure.poliakov.SummaryTask4.logic.admin.editions;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for viewing information about currently edition.
 */

@WebServlet("/editionInfo")
public class EditionInfo extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditionInfo.class);
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("EditionInfo page: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        Integer id = Integer.parseInt(req.getParameter("info"));
        req.getSession().setAttribute(Session.EDITION_INFO, editionDAO.getEditionInfo(id));
        req.getSession().setAttribute("subList", userDAO.getSubscribers(id));

        // todo
        try {
            req.getSession().setAttribute(Session.COUNT_UNSUBSCRIBERS,
                    editionDAO.getEditionUnsubscribers(id).getCountUnsubscribers());
        } catch (NullPointerException e) {
            req.getSession().setAttribute(Session.COUNT_UNSUBSCRIBERS, 0);
                    log.error("The edition have not unsubscribers");
        }
        req.getRequestDispatcher(WebPath.EDITION_INFO_PAGE).forward(req, resp);
    }
}
