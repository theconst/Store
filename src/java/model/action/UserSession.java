/*
 * UserSession.java 
 */
package model.action;

import dao.DataAccessException;
import model.entity.*;

/**
 * User session is responsible for handling user's session
 *
 * @author Kostiantyn Kovalchuk
 */
public class UserSession {

    /**
     * State of the current session
     */
    protected AbstractSessionState state;

    /**
     * Builds new user session
     */
    public UserSession() {
        this.state = new UnautorizedSessionState(this);
    }

    /**
     * Factory method that produces new Session
     *
     * @return new user session
     */
    public static UserSession getSession() {
        return new UserSession();
    }

    /**
     * Logout from the system
     *
     * @throws AccessDeniedException
     */
    public void logout() throws AccessDeniedException {
        state.logout();
    }

    /**
     * Login to the system
     *
     * @param login user's login
     * @param password user's password
     * @throws dao.DataAccessException if error processing the data
     */
    public void login(String login, String password) throws DataAccessException {
        state.login(login, password);
    }

    /**
     * Add good to the cart
     *
     * @param good good to be added
     * @throws AccessDeniedException if the user if not allowed
     */
    public void addToCart(Good good) throws AccessDeniedException {
        state.addToCart(good.getId());
    }

    /**
     * Remove good from cart
     *
     * @param goodId id of the good
     * @throws AccessDeniedException if the user is not allowed
     */
    public void addToCart(int goodId) throws AccessDeniedException {
        state.addToCart(goodId);
    }

    /**
     * Remove good from cart
     *
     * @param goodId id of the good
     * @throws AccessDeniedException if the user is not allowed to
     */
    public void removeFromCart(int goodId) throws AccessDeniedException {
        state.removeFromCart(goodId);
    }

    /**
     * Submit the order
     *
     * @throws DataAccessException if no data is written
     * @throws AccessDeniedException if the user is not allowed to
     */
    public void submitOrder() throws DataAccessException, AccessDeniedException {
        state.submitOrder();
    }

    /**
     * Create new order
     *
     * @throws AccessDeniedException if the user is not allowed to
     */
    public void createOrder() throws AccessDeniedException {
        state.createOrder();
    }

    /**
     * Add customer to the blacklist
     *
     * @param customerId id of the customer
     * @throws AccessDeniedException if the user is not allowed to
     * @throws dao.DataAccessException if error in processing the data
     */
    public void addToBlacklist(int customerId) throws AccessDeniedException,
            DataAccessException {
        state.addToBlacklist(customerId);
    }

    /**
     * Remove customer from the blacklist
     *
     * @param customerId customer id
     * @throws AccessDeniedException if the user is not allowed to
     * @throws dao.DataAccessException if error processing the data
     */
    public void removeFromBlacklist(int customerId) throws AccessDeniedException,
            DataAccessException {
        state.removeFromBlacklist(customerId);
    }

    /**
     * Set state of the current session
     *
     * @param state new state
     */
    public void setState(AbstractSessionState state) {
        this.state = state;
    }

    /**
     * Gets the user of the current session
     *
     * @return the user
     */
    public User getUser() {
        return state.getUser();
    }

    /**
     * Gets the shopping cart if avaliable
     *
     * @return shopping cart
     * @throws DataAccessException if error processing the data
     */
    public ShoppingCart getShoppingCart() throws DataAccessException {
        return state.getShoppingCart();
    }

    /**
     * Gets the user's order
     *
     * @return user's order
     * @throws DataAccessException if no data available
     */
    public PurchaseOrder getOrder() throws DataAccessException {
        return state.getOrder();
    }

    
    /**
     * @return true if the user is customer
     */
    public boolean isCustomer() {
        return state.isCustomer();
    }
    
    /**
     * @return true if the user is admin, false otherwise
     */
    public boolean isAdmin() {
        return state.isAdmin();
    }
}
