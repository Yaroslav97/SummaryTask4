package ua.nure.poliakov.SummaryTask4.dao.close;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class contains methods for close active connection.
 */

public class Close {

    private static final Logger log = Logger.getLogger(Class.class);

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Cannot close a connection", e);
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Cannot close a statement", e);
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("Cannot close a result set", e);
            }
        }
    }
}