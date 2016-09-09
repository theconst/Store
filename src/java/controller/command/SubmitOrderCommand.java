/*
 * SubmitOrderCommand.java 19.01.2016
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import dao.DataAccessException;
import model.action.AccessDeniedException;

/**
 * Submits user's order
 *
 * @author Kostiantyn Kovalchuk
 */
public class SubmitOrderCommand extends ActionCommand {

    @Override
    protected void handleModel(ServletRequestWrapper request)
            throws AccessDeniedException, DataAccessException {
        userSession.submitOrder();
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.CONFIRMATION_PAGE.toString();
    }

}
