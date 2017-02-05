package ua.nure.poliakov.SummaryTask4.dao.rollback;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Rollback {

    private static final Logger log = Logger.getLogger(Rollback.class);

    public static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error("Cannot rollback transaction", e);
            }
        }
    }
}
