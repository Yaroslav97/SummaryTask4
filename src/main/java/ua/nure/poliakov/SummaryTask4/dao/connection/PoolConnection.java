package ua.nure.poliakov.SummaryTask4.dao.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import javax.naming.*;

public class PoolConnection {

    private static final Logger log = Logger.getLogger(PoolConnection.class);

    private PoolConnection() {
    }

    public static ComboPooledDataSource getPool() {
        ComboPooledDataSource dataSource = null;
        try {
            Context context = new InitialContext();
            dataSource = (ComboPooledDataSource) context.lookup("java:/comp/env/jdbc/periodical");
        } catch (NamingException e) {
            log.error("Cannot obtain a connection from the pool", e);
        }
        return dataSource;
    }
}
