package ua.nure.poliakov.SummaryTask4.logic.user;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cabinet for user.
 */

@WebServlet("/userCabinet")
public class UserCabinet extends HttpServlet {

    private static final Logger log = Logger.getLogger(UserCabinet.class);
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("subscribesList",
                editionDAO.getAllSubscriptions(String.valueOf(req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN))));
        log.debug("UserCabinet: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        req.getRequestDispatcher(WebPath.USER_CABINET_PAGE).forward(req, resp);
    }
}
