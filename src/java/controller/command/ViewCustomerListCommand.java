/*
 * ViewCustomerListCommand.java
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import model.action.AccessDeniedException;

/**
 * Commands that views the customer list
 * 
 * @author Kostiantyn Kovalchuk
 */
public class ViewCustomerListCommand extends ActionCommand {
    
    
    /**
     * Error message if the user is not permitted to view the content
     */
    private static String MESSAGE = "You do not have enough rights to " 
            + "view this page";

    @Override
    protected void handleModel(ServletRequestWrapper request) throws AccessDeniedException {
        if (!userSession.isAdmin()) {
            throw new AccessDeniedException(MESSAGE);
        }
    }
    
    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.CUSTOMERLIST_PAGE.toString();
    }

}
