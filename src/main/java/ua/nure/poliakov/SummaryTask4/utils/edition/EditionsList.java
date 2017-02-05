package ua.nure.poliakov.SummaryTask4.utils.edition;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;

import javax.servlet.http.HttpServletRequest;

/**
 * Obtain all list of editions by some sort.
 */

public class EditionsList {

    private static final Logger log = Logger.getLogger(EditionsList.class);
    private static EditionDAO editionDAO = EditionDAOImplement.getInstance();

    public static void editionList(HttpServletRequest req) {
        String sort = req.getParameter("sort");
        String search = req.getParameter("search");
        String filter1 = req.getParameter("filter1");
        String filter2 = req.getParameter("filter2");
        String subject = req.getParameter("subject");
        req.getSession().setAttribute("countSub", editionDAO.getAllSortEditions("rank"));

        if (sort != null) {
            log.debug("Sort list by " + sort);
            req.getSession().setAttribute(Session.EDITION_LIST, editionDAO.getAllSortEditions(sort));
        } else if (search != null) {
            log.debug("Search edition: " + search);
            req.getSession().setAttribute(Session.EDITION_LIST, editionDAO.searchByName(search));
        } else if (filter1 != null && filter2 != null && Double.valueOf(filter1) < Double.valueOf(filter2)) {
            log.debug("Filter edition by price from " + filter1 + " to " + filter2);
            req.getSession().setAttribute(Session.EDITION_LIST,
                    editionDAO.filterByPrice(Double.valueOf(filter1), Double.valueOf(filter2)));
        } else if (subject != null) {
            req.getSession().setAttribute(Session.EDITION_LIST, editionDAO.getEditionsBySubject(subject));
        } else {
            req.getSession().setAttribute(Session.EDITION_LIST, editionDAO.getAllSortEditions("subject"));
        }
    }
}
