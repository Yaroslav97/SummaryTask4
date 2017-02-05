package ua.nure.poliakov.SummaryTask4.logic.common;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.edition.EditionsList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obtain edition list.
 */

@WebServlet("/index")
public class Index extends HttpServlet {

    private static final Logger log = Logger.getLogger(Index.class);
    private UserDAO userDAO = UserDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EditionsList.editionList(req);
        String login = (String) req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN);
        resp.setIntHeader("Refresh", 100);
        if (login != null) {
            req.getSession().setAttribute(Session.AUTHENTICATED_BAN, userDAO.getByLogin(login).getBan());
        }
        log.debug("Index page");
        req.getRequestDispatcher(WebPath.INDEX_PAGE).forward(req, resp);
    }
}
