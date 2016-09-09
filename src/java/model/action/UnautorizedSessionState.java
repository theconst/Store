/*
 * Unatorized.java 16.01.2015
 */
package model.action;

import dao.DataAccessException;
import model.entity.*;

/**
 * Session state when the user has not yet been authorized
 *
 * @author Kostiantyn Kovalchuk
 */
class UnautorizedSessionState extends AbstractSessionState {

    /**
     * Constructs the new unauthorized object
     *
     * @param session the user's session
     */
    public UnautorizedSessionState(UserSession session) {
        super(session);
    }

    @Override
    public void login(String login, String password) throws DataAccessException {

        Permission permission = Permission.UNAUTHENTICATED;
        User user = DataAccessFacade.getUser(login);
        
        /* authentificate user */
        if (user != null) {
            if (user.getPassword().equals(password)) {
                permission = Permission.valueFrom(user.getPermission());
            }
        }
       
        switch (permission) {
            case CUSTOMER:
                Customer customer = DataAccessFacade.getCustomer(user);
                if (customer.isInBlackList()) { 
                    session.setState(this);
                    return;
                }
                session.setState(new CustomerSessionState(session, customer));
                break;
            case ADMIN:
                session.setState(new AdminSessionState(session, user));
                break;
            default:

                /*do nothing if the user is unautorized/ unauthentificated */
                break;
        }

    }
}
