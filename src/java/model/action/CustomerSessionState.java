/*
 * CustomerSessionState.java 19.01.2016
 */
package model.action;

import dao.DataAccessException;
import model.entity.Customer;
import model.entity.PurchaseOrder;
import model.entity.ShoppingCart;
import model.entity.User;

/**
 * Session state in case when the customer has logged in
 *
 * @author Kostiantyn Kovalchuk
 */
public class CustomerSessionState extends AbstractSessionState {

    /**
     * Customer
     */
    private Customer customer;
    
    /**
     * Shopping cart
     */
    private ShoppingCart shoppingCart;
    
    /**
     * Flag that is true of and only if the order was validated
     */
    private boolean orderValidated;
    
    /**
     * Customer's purchase order
     */
    private PurchaseOrder order;

    
    /**
     * Creates a state
     * 
     * @param session session
     * @param customer logged customer
     */
    public CustomerSessionState(UserSession session, Customer customer) {
        super(session);
        this.customer = customer;
        shoppingCart = new ShoppingCart();
        orderValidated = false;
    }

    @Override
    public void addToCart(int goodId) {
        guard();
        shoppingCart.addGood(goodId, 1);
    }

    @Override
    public void removeFromCart(int goodId) {
        guard();
        shoppingCart.removeGood(goodId, 1);
    }

    @Override
    public void login(String login, String password) throws DataAccessException {
        logout();
        session.login(login, password);
    }

    @Override
    public void submitOrder() throws DataAccessException {
        guard();
        if (!shoppingCart.isEmpty()) {
            shoppingCart = DataAccessFacade.createShoppingCart(shoppingCart);
            order = DataAccessFacade.createOrder(customer, shoppingCart);
        }
        orderValidated = true;
    }

    @Override
    public void logout() {
        session.setState(new UnautorizedSessionState(session));
    }

    @Override
    public User getUser() {
        return customer;
    }

    @Override
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    @Override
    public PurchaseOrder getOrder() {
        if (orderValidated) {
            return order;
        }
        return null;
    }
 
    
    /**
     * Auxiliary method that forbids changes to the validated order
     */
    private void guard() {
        if (orderValidated) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void createOrder() {
        shoppingCart = new ShoppingCart();
        order = null;
        orderValidated = false;
    }

    @Override
    public boolean isCustomer() {
        return true;
    }
    
}
