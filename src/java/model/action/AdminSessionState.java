/*
 * AdminSessionState.java 19.01.2016
 */
package model.action;

import dao.DataAccessException;
import model.entity.Customer;
import model.entity.User;

/**
 * Action if the user is admin
 * 
 * @author Kostiantyn Kovalchuk
 */
class AdminSessionState extends AbstractSessionState {

    /**
     * Link to the user's account
     */
    private User admin;

    
    /**
     * Creates new session for the administrator
     * 
     * @param session user's session
     * @param admin admin account
     */
    public AdminSessionState(UserSession session, User admin) {
        super(session);
        this.admin = admin;
    }

    @Override
    public void login(String login, String password) throws DataAccessException {
        logout();
        session.login(login, password);
    }

    @Override
    public void addToBlacklist(int customerId) throws DataAccessException {
        Customer customer = DataAccessFacade.getCustomer(customerId);
        customer.setInBlackList(true);
        DataAccessFacade.updateCustomer(customer);
    }

    @Override
    public void removeFromBlacklist(int customerId) throws DataAccessException {
        Customer customer = DataAccessFacade.getCustomer(customerId);
        customer.setInBlackList(false);
        DataAccessFacade.updateCustomer(customer);
    }

    @Override
    public void logout() {
        session.setState(new UnautorizedSessionState(session));
    }

    @Override
    public User getUser() {
        return admin;
    }

    
    @Override
    public boolean isAdmin() {
        return true;
    }
}
