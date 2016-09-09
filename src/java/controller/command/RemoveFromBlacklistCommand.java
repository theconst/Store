/*
 * RemoveGromBlacklistCommand.java
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import dao.DataAccessException;
import model.action.AccessDeniedException;

/**
 * Removes user from the blacklist
 * 
 * @author Kostiantyn Kovalchuk
 */
public class RemoveFromBlacklistCommand extends ActionCommand {

    /**
     * Blacklisted user request parameter (user to blacklist)
     */
    private static final String BLACLISTED_USER = "blacklisteduser";

    @Override
    protected void handleModel(ServletRequestWrapper request) throws AccessDeniedException, DataAccessException {
        String customerIdStr = request.getParameter(BLACLISTED_USER);
        int customerId = Integer.parseInt(customerIdStr);

        userSession.removeFromBlacklist(customerId);
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.BLACKLIST_PAGE.toString();
    }

}
