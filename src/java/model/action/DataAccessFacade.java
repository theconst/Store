/*
 * DataAccessFacade.java 11.06.2016
 */
package model.action;

import dao.DataAccessException;
import dao.UserDAO;
import dao.ShoppingCartDAO;
import dao.PurchaseOrderDAO;
import dao.DAOFactory;
import dao.CustomerDAO;
import dao.GoodDAO;
import dao.PoolWrapper;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import model.entity.*;

/**
 * Facade for accessing the data
 *
 * @author Kostiantyn Kovalchuk
 */
public class DataAccessFacade {

    /**
     * Gets all goods in the store
     *
     * @return all goods in the store
     * @throws DataAccessException if error accessing the data
     */
    public static List<Good> getGoods() throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            GoodDAO goodDAO = DAOFactory.getGoodDAO(con);

            return goodDAO.findAll();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets all the customers
     *
     * @return all customers in the store
     * @throws DataAccessException if error accessing the database
     */
    public static List<Customer> getCustomers() throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            CustomerDAO customerDAO = DAOFactory.getCustomerDAO(con);
            List<Customer> result = customerDAO.findAll();

            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets all the blacklisted customers
     *
     * @return blacklisted customers
     * @throws DataAccessException if error in accessing the database
     */
    public static List<Customer> getBlaclistedCustomers()
            throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            CustomerDAO customerDAO = DAOFactory.getCustomerDAO(con);
            List<Customer> customerList = customerDAO.findAll();
            Iterator<Customer> it = customerList.iterator();

            while (it.hasNext()) {
                Customer c = it.next();
                if (!c.isInBlackList()) {
                    it.remove();
                }
            }
            return customerList;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets customer by id
     *
     * @param id customer's id
     * @return customer with corresponding id or null
     * @throws DataAccessException if error accessing the data
     */
    public static Customer getCustomer(int id) throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            CustomerDAO customerDAO = DAOFactory.getCustomerDAO(con);
            Customer result = customerDAO.find(id);
            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets the customer by the user's account
     *
     * @param user user of the system
     * @return customer account corresponding to the user, or null otherwise
     * @throws DataAccessException if error accessing the data
     */
    public static Customer getCustomer(User user) throws DataAccessException {  //V
        try (Connection con = PoolWrapper.getConnection()) {
            CustomerDAO customerDAO = DAOFactory.getCustomerDAO(con);
            Customer result = customerDAO.find(user);

            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets the user by login
     *
     * @param login user's login
     * @return user with th specified login, or null
     * @throws dao.DataAccessException if error accessing the data
     */
    public static User getUser(String login) throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            UserDAO userDAO = DAOFactory.getUserDAO(con);
            User result = userDAO.getByLogin(login);

            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets the shopping cart by its id
     *
     * @param id id of the shopping cart
     * @return shopping cart
     * @throws DataAccessException if no such cart exists
     */
    public static ShoppingCart getShoppingCart(int id)
            throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            ShoppingCartDAO cartDAO = DAOFactory.getShoppingCartDAO(con);
            ShoppingCart result = cartDAO.find(id);

            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Get the shopping cart by the order
     *
     * @param order shopping cart order
     * @return shopping cart corresponding to the order
     * @throws DataAccessException if error accessing the data
     */
    public static ShoppingCart getShoppingCart(PurchaseOrder order)
            throws DataAccessException {
        return getShoppingCart(order.getShoppingCartId());
    }

    /**
     * Creates the user in the database
     *
     * @param user user new user to create
     * @throws DataAccessException if no such user exists
     */
    public static void createUser(User user) throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            UserDAO userDAO = DAOFactory.getUserDAO(con);
            userDAO.create(user, true);
            con.commit();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Creates the customer in the database
     *
     * @param customer new customer
     * @throws DataAccessException if no such customer exists
     */
    public static void createCustomer(Customer customer)
            throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            CustomerDAO customerDAO = DAOFactory.getCustomerDAO(con);
            customerDAO.create(customer);
            con.commit();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Creates the shopping cart in the database with the default id and sets
     * the id of the cart
     *
     * @param sc shopping cart
     * @return creates object
     * @throws DataAccessException if error accessing the data
     */
    public static ShoppingCart createShoppingCart(ShoppingCart sc)
            throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            ShoppingCartDAO cartDAO = DAOFactory.getShoppingCartDAO(con);
            int id;

            id = cartDAO.create(sc, true);
            con.commit();
            sc.setId(id);
            return sc;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Creates order with the default number and assigns it to the order
     *
     * @param customer customer
     * @param sc shopping cart
     * @return created order
     * @throws DataAccessException if error accessing the data
     */
    public static PurchaseOrder createOrder(Customer customer, ShoppingCart sc) //V
            throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            PurchaseOrderDAO orderDAO = DAOFactory.getPurchaseOrderDAO(con);
            PurchaseOrder order = new PurchaseOrder();
            int orderNumber;

            order.setCustromerId(customer.getId());
            order.setShoppingCartId(sc.getId());
            orderNumber = orderDAO.create(order, PurchaseOrderDAO.USE_DEFAULT_KEY);
            order.setNumber(orderNumber);
            con.commit();
            return order;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Updates the customer entry
     *
     * @param customer customer to be updated
     * @return old customer's value
     * @throws DataAccessException if error accessing the data
     */
    public static Customer updateCustomer(Customer customer) throws DataAccessException { //V
        try (Connection con = PoolWrapper.getConnection()) {
            CustomerDAO customerDAO = DAOFactory.getCustomerDAO(con);
            Customer result = customerDAO.update(customer);
            con.commit();
            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * adds good to the cart
     *
     * @param order order
     * @param good good
     * @throws DataAccessException if data access error occured
     */
    public static void addToCart(PurchaseOrder order, Good good)
            throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            ShoppingCartDAO cartDAO = DAOFactory.getShoppingCartDAO(con);
            ShoppingCart sc = getShoppingCart(order);

            sc.addGood(good.getId());
            cartDAO.update(sc);
            con.commit();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Gets good
     *
     * @param goodId id of the good
     * @return good entry from the database
     * @throws DataAccessException if error accessing the data
     */
    public static Good getGood(int goodId) throws DataAccessException {
        try (Connection con = PoolWrapper.getConnection()) {
            GoodDAO goodDAO = DAOFactory.getGoodDAO(con);
            Good good = goodDAO.find(goodId);

            return good;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

}
