package ua.nure.poliakov.SummaryTask4.dao.edition_dao;

import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.DBException;

import java.util.List;

/**
 * Edition DAO. Works with MySQL DB. Interface contain CRUD methods which works with editions.
 *
 * @author Yaroslav Poliakov
 */

public interface EditionDAO {

    void addEdition(Edition edition);

    void updateEdition(Edition edition);

    void deleteEdition(int id) throws DBException;

    Edition getEdition(int id);

    List<Edition> getAllSortEditions(String sort);

    boolean isContains(int id);

    void subscribe(String login, int idEdition);

    boolean isSubscribe(String login, int idEdition);

    List<Edition> getAllSubscriptions(String login);

    void unsubscribe(String login, int idEdition);

    boolean isSameEdition(String name, String subject);

    List<Edition> searchByName(String name);

    List<Edition> filterByPrice(double from, double to);

    List<Edition> getEditionInfo(int id);

    List<Edition> getEditionsBySubject(String subject);

    Edition getEditionUnsubscribers(int id);
}
