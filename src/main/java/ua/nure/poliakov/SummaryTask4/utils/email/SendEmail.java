package ua.nure.poliakov.SummaryTask4.utils.email;

import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class for sending email.
 */

public class SendEmail {

    private static UserDAO userDAO = UserDAOImplement.getInstance();
    private static EditionDAO editionDAO = EditionDAOImplement.getInstance();
    private static final String myEmail = "yaroslav.poliakov@gmail.com";
    private static final String password = "py19970429";

    public static void sendEmail(String email, String message) throws MessagingException {
        Message mimeMessage = new MimeMessage(getSession());
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        mimeMessage.setSubject("Periodicals info");
        mimeMessage.setText(message);
        Transport.send(mimeMessage);
    }

    private static Session getSession() {
        Session session = Session.getDefaultInstance(getProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myEmail, password);
                    }
                });
        return session;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        return properties;
    }

    public static String restoreAccess(String name, String password) {
        return "Hello " + name + ", your new password - " + password + "." + System.lineSeparator() +
                "http://localhost:8080/index";
    }

    public static String subscribeEmail(String login, int id) {
        return "Hello " + userDAO.getByLogin(login).getFullName() + ", you successfully subscribe to " +
                editionDAO.getEdition(id).getName() + " " + editionDAO.getEdition(id).getPrice() + "$.";
    }

    public static String registrationEmail(String login, String email) {
        return "For confirmation registration click on link " + "//http://localhost:8080/link?login=" + login +
                "&email=" + email;
    }

    public static String randomPassword() {
        Random random = new Random();
        return String.valueOf(random.nextInt(8999) + 1000);
    }
}