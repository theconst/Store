/*
 * LogutCommand.java
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import model.action.AccessDeniedException;

/**
 * Logs the user out 
 * 
 * @author Kostiantyn Kovalchuk
 */
public class LogoutCommand extends ActionCommand {

    @Override
    protected void handleModel(ServletRequestWrapper request) throws AccessDeniedException {
        userSession.logout();

    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.WELCOME_PAGE.toString();
    }
}
