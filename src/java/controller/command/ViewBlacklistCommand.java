/*
 * ViewBlacklistCommand.java 19.01.2016
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import model.action.AccessDeniedException;

/**
 * Command that views the blacklist
 *
 * @author Kostiantyn Kovalchuk
 */
public class ViewBlacklistCommand extends ActionCommand {
    
    private static final String MESSAGE = "You are not allowed to view this "
            + "content";

    @Override
    protected void handleModel(ServletRequestWrapper request) throws AccessDeniedException {
        if (!userSession.isAdmin()) {
            throw new AccessDeniedException(MESSAGE);
        }         
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.BLACKLIST_PAGE.toString();
    }

}
