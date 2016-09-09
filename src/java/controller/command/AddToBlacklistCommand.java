/*
 * AddToBlacklistCommand.java 19.01.2016
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import dao.DataAccessException;
import model.action.AccessDeniedException;

/**
 * Adds customer to blacklist
 * 
 * @author Kostiantyn Kovalchuk
 */
public class AddToBlacklistCommand extends ActionCommand {

    /**
     * Parameter of the request that identifies the blacklisted user
     */
    private static final String BLACLISTED_USER = "blacklisteduser";

    @Override
    protected void handleModel(ServletRequestWrapper request) 
            throws AccessDeniedException, DataAccessException {
        String customerIdStr = request.getParameter(BLACLISTED_USER);
        int customerId = Integer.parseInt(customerIdStr);
        
        userSession.addToBlacklist(customerId);
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.CUSTOMERLIST_PAGE.toString();
    }

}
