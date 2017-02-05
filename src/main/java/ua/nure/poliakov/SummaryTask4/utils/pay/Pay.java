package ua.nure.poliakov.SummaryTask4.utils.pay;

import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;

public class Pay {

    private static UserDAO userDAO= UserDAOImplement.getInstance();
    private static EditionDAO editionDAO = EditionDAOImplement.getInstance();

    public static boolean isCanPay(String login, int idEdition) {
        if (userDAO.getByLogin(login).getScore() - editionDAO.getEdition(idEdition).getPrice() >= 0) {
            return true;
        }
        return false;
    }
}
