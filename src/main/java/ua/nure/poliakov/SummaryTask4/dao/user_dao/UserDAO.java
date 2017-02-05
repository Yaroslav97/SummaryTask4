package ua.nure.poliakov.SummaryTask4.dao.user_dao;

import ua.nure.poliakov.SummaryTask4.dao.entity.Score;
import ua.nure.poliakov.SummaryTask4.dao.entity.User;

import java.util.List;

/**
 * User DAO. Works with MySQL DB. Interface contain CRUD methods which works with users.
 *
 * @author Yaroslav Poliakov
 */

public interface UserDAO {

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(String login);

    User getByLogin(String login);

    List<User> getAllUsers();

    void banUser(String login, boolean status);

    boolean isContainsLogin(String login);

    void updatePassword(String login, String password);

    void updateScore(Score score);

    double getScore(String login);

    List<User> getAllUsersByRole(String role, String login);

    void updateSettings(String login, Boolean sendEmail);

    boolean getSettings(String login);

    List<User> getSubscribers(int idEdition);

    List<User> searchByName(String fullName);
}
