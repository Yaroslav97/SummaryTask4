package ua.nure.poliakov.SummaryTask4.logic.admin.editions;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.WebPath;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.ValidateEdition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for editing editions.
 */

@WebServlet("/editEdition")
public class EditEdition extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditEdition.class);
    private EditionDAO editionDAO = EditionDAOImplement.getInstance();
    private Validator<Edition> validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("editId", req.getParameter("eId"));
        session.setAttribute("editName", req.getParameter("eName"));
        session.setAttribute("editSubject", req.getParameter("eSubject"));
        session.setAttribute("editPrice", req.getParameter("ePrice"));
        log.info("EditEdition page: " + req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN));
        req.getRequestDispatcher(WebPath.EDIT_EDITION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String subject = req.getParameter("subject");
        Double price = Double.valueOf(req.getParameter("price"));
        Integer id = Integer.parseInt(String.valueOf(req.getSession().getAttribute("editId")));
        validator = new ValidateEdition();

        try {
            if (validator.validate(new Edition(name, subject, price)) && !editionDAO.isSameEdition(name, subject)) {
                editionDAO.updateEdition(new Edition(id, name, subject, price));
                log.debug("Edition " + editionDAO.getEdition(id).getName() + " was change");
                resp.sendRedirect("/index");
            } else if (editionDAO.isSameEdition(name, subject)) {
                log.debug("The same edition already exist ==> " + req.getSession().getAttribute("editId"));
                req.setAttribute(Session.EDIT_INFO, "The same edition already exist");
                req.getRequestDispatcher(WebPath.EDIT_EDITION_PAGE).forward(req, resp);
            }
        } catch (ValidationException e) {
            log.error("No valid data ==> " + req.getSession().getAttribute("editId"), e);
            req.setAttribute(Session.EDIT_INFO, "No valid data");
            req.getRequestDispatcher(WebPath.EDIT_EDITION_PAGE).forward(req, resp);
        }
    }
}
