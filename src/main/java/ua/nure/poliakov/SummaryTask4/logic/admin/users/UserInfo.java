package ua.nure.poliakov.SummaryTask4.logic.admin.users;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for reveiwing information about currently user.
 */

@WebServlet("/userInfo")
public class UserInfo extends HttpServlet {

    private static final Logger log = Logger.getLogger(UserInfo.class);
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        req.getSession().setAttribute("usersInfo", editionDAO.getAllSubscriptions(login));
        req.getSession().setAttribute("count", editionDAO.getAllSubscriptions(login).size());
        req.setAttribute("fullName", req.getParameter("fullName"));
        req.setAttribute("login", login);
        log.debug("Information about ==> " + login);
        req.getRequestDispatcher(WebPath.USER_PROFILE_PAGE).forward(req, resp);
    }
}
