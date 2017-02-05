package ua.nure.poliakov.SummaryTask4.logic.admin.editions;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for removal editions from editions list.
 */

@WebServlet("/removeEdition")
public class RemoveEdition extends HttpServlet {

    private static final Logger log = Logger.getLogger(RemoveEdition.class);
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String name = editionDAO.getEdition(id).getName();

        log.debug("RemoveEdition: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));

        try {
            if (editionDAO.isContains(id)) {
                editionDAO.deleteEdition(id);
                log.debug("Edition " + name + "[" + id + "] was delete");
                resp.sendRedirect("/index");
            } else {
                log.debug("Edition " + id + " not exist");
                resp.sendRedirect("/index");
            }
        } catch (DBException e) {
            log.debug("Cannot remove edition");
            req.setAttribute(Session.SUBSCRIBE_INFO, "Cannot remove edition");
            req.getRequestDispatcher(WebPath.INDEX_PAGE).forward(req, resp);
        }
    }
}
