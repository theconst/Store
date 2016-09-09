/*
 * DAOFactory.java 18.01.2016
 */
package dao;

import java.sql.Connection;

/**
 * Factory of the data access Objects
 *
 * @author Kostiantyn Kovalchuk
 */
public class DAOFactory {

    /**
     * Constructs the new factory
     */
    private DAOFactory() {

        /* do nothing - factory has nothing to initialize */
    }

    /**
     * Creates new user dao
     *
     * @param con connection to the database
     * @return data access object for the user
     * @throws DataAccessException if error in establishing the connection
     */
    public static UserDAO getUserDAO(Connection con) throws DataAccessException {
        return new UserDAO(con);
    }

    /**
     * Creates new customer dao
     *
     * @param con connection to the database
     * @return data access object for the customer
     * @throws DataAccessException if error in establishing the connection
     */
    public static CustomerDAO getCustomerDAO(Connection con) throws DataAccessException {
        return new CustomerDAO(con);
    }

    /**
     * Gets the shopping cart dao
     *
     * @param con connection to the database
     * @return data access object for the shopping cart
     * @throws DataAccessException if error in establishing the connection
     */
    public static ShoppingCartDAO getShoppingCartDAO(Connection con) throws DataAccessException {
        return new ShoppingCartDAO(con);
    }

    /**
     * Gets the purchase order DAO
     *
     * @param con connection to the database
     * @return data access object for the purchase order
     * @throws DataAccessException if error in establishing the connection
     */
    public static PurchaseOrderDAO getPurchaseOrderDAO(Connection con) throws DataAccessException {
        return new PurchaseOrderDAO(con);
    }

    /**
     * Creates the new goodDAO
     *
     * @param con connection to the database
     * @return data access object for the good
     * @throws DataAccessException if error in establishing the connection
     */
    public static GoodDAO getGoodDAO(Connection con) throws DataAccessException {
        return new GoodDAO(con);
    }
}
