package ua.nure.poliakov.SummaryTask4.logic.user;

import com.itextpdf.text.DocumentException;
import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.logic.common.paths.Session;
import ua.nure.poliakov.SummaryTask4.utils.report.GenerateReport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Generate and download pdf report.
 */

@WebServlet("/report")
public class Report extends HttpServlet {

    private static final Logger log = Logger.getLogger(Logger.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute(Session.AUTHENTICATED_LOGIN);
        try {
            GenerateReport.generate(login);
            log.debug("Generate report for " + login);
        } catch (DocumentException e) {
            log.error("Cannot generate report", e);
        }
        GenerateReport.downloadReport(resp, login);
    }
}
