/*
 * SessionInfoViewAdapter 19.01.2016
 */
package view;

import dao.DataAccessException;
import model.action.UserSession;
import model.entity.PurchaseOrder;
import model.entity.ShoppingCart;
import model.entity.User;

/**
 * Adapts the view for the customer
 *
 * @author Kostiantyn Kovalchuk
 */
public class SessionInfoViewAdapter {

    /**
     * User session for the view
     */
    private final UserSession session;

    public SessionInfoViewAdapter(UserSession session) {
        this.session = session;
    }

    /**
     * Gets the user of the current session
     *
     * @return the user
     */
    public User getUser() {
        return session.getUser();
    }

    /**
     * Gets the shopping cart if avaliable
     *
     * @return shopping cart
     * @throws DataAccessException
     */
    public ShoppingCart getShoppingCart() throws DataAccessException {
        return session.getShoppingCart();
    }

    /**
     * Gets the user's order
     *
     * @return user's order
     * @throws DataAccessException if no data available
     */
    public PurchaseOrder getOrder() throws DataAccessException {
        return session.getOrder();
    }

    /**
     * Gets the bill corresponding to the shopping cart
     *
     * @return bill returns bill for the shopping cart
     * @throws dao.DataAccessException
     */
    public Bill getBill() throws DataAccessException {
        return Bill.receiveBill(getShoppingCart());
    }

    /**
     * Factory method that produces the adaptor for the specified session
     *
     * @param session
     * @return
     */
    public static SessionInfoViewAdapter getAdapter(UserSession session) {
        return new SessionInfoViewAdapter(session);
    }

    /**
     * Get the session that the adapter adapts
     * 
     * @return 
     */
    public UserSession getUserSession() {
        return session;
    }
}
