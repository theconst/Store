/*
 * AbstractSessionState.java
 */
package model.action;

import dao.DataAccessException;
import model.entity.PurchaseOrder;
import model.entity.ShoppingCart;
import model.entity.User;

/**
 * State of the user session
 *
 * @author Kostiantyn Kovalchuk
 */
public abstract class AbstractSessionState {

    /**
     * Message for the access denial
     */
    private static final String ACCESS_DENIED_MESSAGE
            = "You do not have enough rights to perform this action";

    /**
     * Session with which this state is associated
     */
    protected UserSession session;

    /**
     * Forbids the action for the current state
     *
     * @throws AccessDeniedException
     */
    private static void forbid() throws AccessDeniedException {
        throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
    }

    /**
     * Constructs the state associated with session
     *
     * @param session session for the state
     */
    public AbstractSessionState(UserSession session) {
        this.session = session;
    }

    /**
     * Logins the user
     *
     * @param login user's login
     * @param password user's password
     * @throws dao.DataAccessException
     */
    public abstract void login(String login, String password) throws DataAccessException;

    /**
     * logs the user out
     *
     * @throws AccessDeniedException
     */
    public void logout() throws AccessDeniedException {
        // does nothing by default
    }

    /**
     * Adds the good to the user's cart
     *
     * @param goodId
     * @throws AccessDeniedException
     */
    public void addToCart(int goodId) throws AccessDeniedException {
        forbid();                                       //forbidden by default
    }

    /**
     * Adds the specified good to the cart
     *
     * @param goodId
     * @throws AccessDeniedException
     */
    public void removeFromCart(int goodId) throws AccessDeniedException {
        forbid();                                       //forbidden by default
    }

    /**
     * Submits the order
     *
     * @throws DataAccessException
     * @throws AccessDeniedException
     */
    public void submitOrder() throws DataAccessException, AccessDeniedException {
        forbid();                                        //forbidden by default
    }

    /**
     * Add the specified customer to blacklist
     *
     * @param customerId
     * @throws AccessDeniedException
     * @throws dao.DataAccessException
     */
    public void addToBlacklist(int customerId) throws AccessDeniedException,
            DataAccessException {
        forbid();                                       //forbidden by default
    }

    /**
     * Removes the specified customer from the blacklist
     *
     * @param customerId
     * @throws AccessDeniedException
     * @throws dao.DataAccessException
     */
    public void removeFromBlacklist(int customerId) throws AccessDeniedException,
            DataAccessException {
        forbid();                                       //forbidden by default
    }

    /**
     * create the order if the user is allowed to
     *
     * @throws AccessDeniedException
     */
    public void createOrder() throws AccessDeniedException {
        forbid();                                       //forbidden by default
    }

    /**
     * Gets the user
     *
     * @return user of the session
     */
    public User getUser() {
        return null;
    }

    /**
     * Gets the shopping cart
     *
     * @return shopping cart if the user has it
     * @throws DataAccessException
     */
    public ShoppingCart getShoppingCart() throws DataAccessException {
        return null;
    }

    /**
     * Gets the order
     *
     * @return order if the user is allowed to
     * @throws DataAccessException if error accessing the data occured
     */
    public PurchaseOrder getOrder() throws DataAccessException {
        return null;                                  //returns null by default
    }

    /**
     * @return true if the user is customer, false otherwise
     */
    boolean isCustomer() {
        return false;
    }

    /**
     * @return true if the user is admin, false otherwise
     */
    boolean isAdmin() {
        return false;
    }

}
