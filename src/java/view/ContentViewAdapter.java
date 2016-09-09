/*
 * ContentViewAdapter.java 19.01.2016
 */
package view;

import dao.DataAccessException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.action.DataAccessFacade;
import model.entity.*;

/**
 * Class with getters/setters that view request-specific content (not associated
 * with session)
 *
 * @author Kostiantyn Kovalchuk
 */
public class ContentViewAdapter {

    /**
     * Instance of the view adapter
     */
    private static ContentViewAdapter instance = new ContentViewAdapter();

    /**
     * Gets the instance of the adapter
     * 
     * @return instance of the adapter
     */
    public static ContentViewAdapter getInstance() {
        return instance;
    }

    /**
     * Gets list of all customers
     * @return 
     */
    public List<Customer> getCustomers() {
        try {
            return DataAccessFacade.getCustomers();
        } catch (DataAccessException ex) {
            Logger.getLogger(ContentViewAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets list of all goods
     * @return list of all the goods
     */
    public List<Good> getGoods() {
        try {
            return DataAccessFacade.getGoods();
        } catch (DataAccessException ex) {
            Logger.getLogger(ContentViewAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets list of all blacklisted  customers
     * @return list of all blacklisted customers
     */
    public List<Customer> getBlacklist() {
        try {
            return DataAccessFacade.getBlaclistedCustomers();
        } catch (DataAccessException ex) {
            Logger.getLogger(ContentViewAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
