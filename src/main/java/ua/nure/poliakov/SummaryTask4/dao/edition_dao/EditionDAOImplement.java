package ua.nure.poliakov.SummaryTask4.dao.edition_dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import ua.nure.poliakov.SummaryTask4.dao.close.Close;
import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.dao.connection.PoolConnection;
import ua.nure.poliakov.SummaryTask4.dao.entity.Score;
import ua.nure.poliakov.SummaryTask4.dao.rollback.Rollback;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAO;
import ua.nure.poliakov.SummaryTask4.dao.user_dao.UserDAOImplement;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditionDAOImplement implements EditionDAO {

    private EditionDAOImplement() {
    }

    private static EditionDAOImplement instance;

    public static synchronized EditionDAOImplement getInstance() {
        if (instance == null) {
            instance = new EditionDAOImplement();
        }
        return instance;
    }

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String PRICE = "price";
    private static final String REVERSE_PRICE = "reverse price";
    private static final String RANK = "rank";
    private static final String WITHDRAW = "withdraw";
    private static final String LOGIN = "login";
    private static final String EDITION = "edition";
    private static final String EDITIONS_ID = "editions.id";
    private static final String EDITIONS_NAME = "editions.name";
    private static final String EDITIONS_SUBJECT = "editions.subject";

    private static final Logger log = Logger.getLogger(EditionDAOImplement.class);

    private static final String INSERT_INTO_EDITIONS = "INSERT INTO editions (`name`, subject, price) VALUES(?,?,?)";
    private static final String INSERT_INTO_SUBSCRIBES = "INSERT INTO subscribes (login, edition, status) VALUES(?,?,1)";
    private static final String UPDATE_EDITIONS = "UPDATE editions SET name=?, subject=?, price=? WHERE id=?";
    private static final String UPDATE_SUBSCRIBES = "UPDATE subscribes SET status=0 WHERE login=? AND edition=?";
    private static final String DELETE_EDITIONS = "DELETE FROM editions WHERE id=?";
    private static final String DELETE_SUBSCRIBE = "DELETE FROM subscribes WHERE edition=?";
    private static final String SELECT_EDITIONS = "SELECT * FROM editions WHERE id=?";
    private static final String SELECT_ALL_EDITIONS_SORT_BY_ID = "SELECT * FROM editions ORDER BY id";
    private static final String SELECT_ALL_EDITIONS_SORT_BY_NAME = "SELECT * FROM editions ORDER BY name";
    private static final String SELECT_ALL_EDITIONS_SORT_BY_SUBJECT = "SELECT * FROM editions ORDER BY subject";
    private static final String SELECT_ALL_EDITIONS_SORT_BY_PRICE = "SELECT * FROM editions ORDER BY price";
    private static final String SELECT_ALL_EDITIONS_REVERSE_SORT_BY_PRICE = "SELECT * FROM editions ORDER BY price DESC";
    private static final String SELECT_SUBSCRIBE =
            "SELECT login, edition FROM subscribes WHERE login=? AND edition=? AND status=1";
    private static final String SELECT_ID = "SELECT id FROM editions WHERE id=?";
    private static final String SELECT_ALL_SUBSCRIPTIONS_BY_LOGIN = "SELECT * FROM subscribes WHERE login=? AND status=1";
    private static final String SELECT_EDITION_BY_NAME_AND_SUBJECT = "SELECT * FROM editions WHERE `name`=? && subject=?";
    private static final String SELECT_BY_NAME = "SELECT * FROM editions WHERE name LIKE ? ORDER BY name";
    private static final String SELECT_BY_PRICE = "SELECT * FROM editions WHERE price BETWEEN ? AND ? ORDER BY price";
    private static final String SELECT_BY_SUBSCRIPTIONS =
            "SELECT editions.*, count(subscribes.login) FROM editions, subscribes " +
                    "WHERE editions.id = subscribes.edition GROUP BY subscribes.edition ORDER BY count(subscribes.login) DESC";
    private static final String SELECT_EDITION_INFO =
            "SELECT editions.id, editions.name, editions.subject, count(subscribes.login), sum(editions.price) " +
                    "FROM editions, subscribes WHERE editions.id=? AND editions.id = subscribes.edition " +
                    "GROUP BY subscribes.edition ORDER BY editions.name";
    private static final String SELECT_EDITION_BY_SUBJECT = "SELECT * FROM editions WHERE subject=? ORDER BY name";
    
    // todo
    private static final String SELECT_EDITION_COUNT_UNSUBSCRIBE =
            "SELECT editions.id, count(subscribes.login) FROM editions, subscribes " +
                    "WHERE editions.id=? AND subscribes.status=0 AND editions.id = subscribes.edition " +
                    "GROUP BY subscribes.edition ORDER BY editions.id";


    private ComboPooledDataSource dataSource = PoolConnection.getPool();
    private UserDAO userDAO = UserDAOImplement.getInstance();
    private Edition edition;

    @Override
    public void addEdition(Edition edition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_INTO_EDITIONS);
            preparedStatement.setString(1, edition.getName());
            preparedStatement.setString(2, edition.getSubject());
            preparedStatement.setDouble(3, edition.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot add edition", e);
        } finally {
            Close.close(preparedStatement);
            Close.close(connection);
        }
    }

    @Override
    public void updateEdition(Edition edition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_EDITIONS);
            preparedStatement.setString(1, edition.getName());
            preparedStatement.setString(2, edition.getSubject());
            preparedStatement.setDouble(3, edition.getPrice());
            preparedStatement.setInt(4, edition.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update edition", e);
        } finally {
            Close.close(preparedStatement);
            Close.close(connection);
        }
    }

    @Override
    public void deleteEdition(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(DELETE_SUBSCRIBE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_EDITIONS);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            Rollback.rollback(connection);
            log.error("Cannot delete edition", e);
            throw new DBException("Cannot delete edition");
        } finally {
            Close.close(preparedStatement);
            Close.close(connection);
        }
    }

    @Override
    public Edition getEdition(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EDITIONS);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                edition = new Edition(
                        resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        resultSet.getString(SUBJECT),
                        resultSet.getDouble(PRICE));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return edition;
    }

    @Override
    public List<Edition> getAllSortEditions(String sort) {
        List<Edition> editionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            switch (sort) {
                case ID:
                    preparedStatement = connection.prepareStatement(SELECT_ALL_EDITIONS_SORT_BY_ID);
                    break;
                case NAME:
                    preparedStatement = connection.prepareStatement(SELECT_ALL_EDITIONS_SORT_BY_NAME);
                    break;
                case SUBJECT:
                    preparedStatement = connection.prepareStatement(SELECT_ALL_EDITIONS_SORT_BY_SUBJECT);
                    break;
                case PRICE:
                    preparedStatement = connection.prepareStatement(SELECT_ALL_EDITIONS_SORT_BY_PRICE);
                    break;
                case REVERSE_PRICE:
                    preparedStatement = connection.prepareStatement(SELECT_ALL_EDITIONS_REVERSE_SORT_BY_PRICE);
                    break;
                case RANK:
                    preparedStatement = connection.prepareStatement(SELECT_BY_SUBSCRIPTIONS);
                    break;
                default:
                    preparedStatement = connection.prepareStatement(SELECT_ALL_EDITIONS_SORT_BY_SUBJECT);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                editionList.add(new Edition(
                        resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        resultSet.getString(SUBJECT),
                        resultSet.getDouble(PRICE),
                        sort.equals(RANK) ? resultSet.getInt(5) : 0));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain editions", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return editionList;
    }

    @Override
    public boolean isContains(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isContain = false;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(ID) == id) {
                    isContain = true;
                }
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return isContain;
    }

    @Override
    public void subscribe(String login, int idEdition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_INTO_SUBSCRIBES);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, idEdition);
            preparedStatement.executeUpdate();
            userDAO.updateScore(new Score(login, getEdition(idEdition).getPrice(), WITHDRAW));
        } catch (SQLException e) {
            log.error("Cannot subscribe to edition", e);
        } finally {
            Close.close(preparedStatement);
            Close.close(connection);
        }
    }

    @Override
    public boolean isSubscribe(String login, int idEdition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isSubscribe = false;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SUBSCRIBE);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, idEdition);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(LOGIN).equals(login) &&
                        resultSet.getInt(EDITION) == idEdition) {
                    isSubscribe = true;
                }
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return isSubscribe;
    }

    @Override
    public List<Edition> getAllSubscriptions(String login) {
        List<Edition> editionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_SUBSCRIPTIONS_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                editionList.add(new Edition(
                        getEdition(resultSet.getInt(EDITION)).getId(),
                        getEdition(resultSet.getInt(EDITION)).getName(),
                        getEdition(resultSet.getInt(EDITION)).getSubject(),
                        getEdition(resultSet.getInt(EDITION)).getPrice()));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain editions", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return editionList;
    }

    @Override
    public void unsubscribe(String login, int idEdition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SUBSCRIBES);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, idEdition);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete subscription", e);
        } finally {
            Close.close(preparedStatement);
            Close.close(connection);
        }
    }

    @Override
    public boolean isSameEdition(String name, String subject) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean same = false;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EDITION_BY_NAME_AND_SUBJECT);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, subject);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(NAME).equals(name) &&
                        resultSet.getString(SUBJECT).equals(subject)) {
                    same = true;
                }
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return same;
    }

    @Override
    public List<Edition> searchByName(String name) {
        List<Edition> editionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                editionList.add(new Edition(
                        resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        resultSet.getString(SUBJECT),
                        resultSet.getDouble(PRICE)));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition by name", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return editionList;
    }

    @Override
    public List<Edition> filterByPrice(double from, double to) {
        List<Edition> editionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_PRICE);
            preparedStatement.setDouble(1, from);
            preparedStatement.setDouble(2, to);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                editionList.add(new Edition(
                        resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        resultSet.getString(SUBJECT),
                        resultSet.getDouble(PRICE)));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition by price", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return editionList;
    }

    @Override
    public List<Edition> getEditionInfo(int id) {
        List<Edition> editionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EDITION_INFO);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                editionList.add(new Edition(
                        resultSet.getInt(EDITIONS_ID),
                        resultSet.getString(EDITIONS_NAME),
                        resultSet.getString(EDITIONS_SUBJECT),
                        resultSet.getDouble(5),
                        resultSet.getInt(4)));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain edition info", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return editionList;
    }

    @Override
    public List<Edition> getEditionsBySubject(String subject) {
        List<Edition> editionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EDITION_BY_SUBJECT);
            preparedStatement.setString(1, subject);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                editionList.add(new Edition(
                        resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        resultSet.getString(SUBJECT),
                        resultSet.getDouble(PRICE)));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain editions by subject", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return editionList;
    }

    // todo
    @Override
    public Edition getEditionUnsubscribers(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Edition edition = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EDITION_COUNT_UNSUBSCRIBE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                edition = new Edition(resultSet.getInt(2));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain count of unsubscribers", e);
        } finally {
            Close.close(resultSet);
            Close.close(preparedStatement);
            Close.close(connection);
        }
        return edition;
    }
}
