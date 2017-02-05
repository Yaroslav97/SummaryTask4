package ua.nure.poliakov.SummaryTask4.logic.common;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LogOut from profile.
 */

@WebServlet("/logout")
public class LogOut extends HttpServlet {

    private static final Logger log = Logger.getLogger(LogOut.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug(req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN) + " was log out");
        String lang = String.valueOf(req.getSession().getAttribute("lang"));
        req.getSession().invalidate();
        req.getSession().setAttribute("lang", lang);
        resp.sendRedirect("/index");
    }
}
