/*
 * PoolWrapper.java 4.01.2015
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Wrapper for the connection pool
 *
 * @author Kostiantyn Kovalchuk
 */
public class PoolWrapper {

    /**
     * Path to the resource
     */
    private static final String PATH 
            = DatabaseConfigurationManager.getProperty("connection.path");

    /**
     * Data source object for the resource
     */
    private static DataSource ds;

    static {
        InitialContext ctx;
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(PATH);
        } catch (NamingException ex) {
            Logger.getLogger(PoolWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PoolWrapper() {

    }

    /**
     * Get the connection from pool
     * 
     * @return new Connection
     * @throws DataAccessException 
     */
    public static Connection getConnection() throws DataAccessException {
        Connection connection;

        try {
            connection = ds.getConnection();
            return connection;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Drops the connection to the pool
     * 
     * @param connection connection to be closed
     * @throws DataAccessException 
     */
    public static void dropConnection(Connection connection)
            throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

}
