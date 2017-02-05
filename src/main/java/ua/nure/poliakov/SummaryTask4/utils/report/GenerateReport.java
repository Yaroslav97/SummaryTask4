package ua.nure.poliakov.SummaryTask4.utils.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Class for generate report for currently user.
 * Report contain list of all subscriptions.
 */

public class GenerateReport {

    private static final Logger log = Logger.getLogger(GenerateReport.class);
    private static EditionDAO editionDAO = EditionDAOImplement.getInstance();
    private static UserDAO userDAO = UserDAOImplement.getInstance();
    public static final String PATH = "C:/Users/comteh/IdeaProjects/Periodicals/src/main/resources/reports/";

    public static void generate(String login) throws FileNotFoundException, DocumentException {
        List<Edition> list = editionDAO.getAllSubscriptions(login);

        Document doc = new Document(PageSize.A5);
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(String.format("%sreport_%s.pdf", PATH, login)));
        doc.open();
        doc.add(new Paragraph(userDAO.getByLogin(login).getFullName()));
        doc.add(new Paragraph("Subscriptions:"));
        for (Edition edition : list) {
            doc.add(new Paragraph(edition.getName() + ", " + edition.getSubject() + ", " + edition.getPrice() + ";"));
        }
        doc.addCreationDate();
        doc.addCreator("Periodicals");
        doc.close();
        pdfWriter.close();
    }

    public static void downloadReport(HttpServletResponse resp, String login) {
        File file = new File(GenerateReport.PATH);
        resp.setContentType("application/pdf");
        resp.addHeader("ContextFilter-Disposition", "inline; filename=\"" + String.format("report_%s.pdf", login) + "\"");
        resp.setContentLength((int) file.length());
        log.trace("download report");
    }
}
