/*
 * RemoveFromCartCommand.java 
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import model.action.AccessDeniedException;

/**
 * Removes the good from cart
 * 
 * @author Kostiantyn Kovalchuk
 */
class RemoveFromCartCommand extends ActionCommand {

    
    /**
     * Good id parameter of the request
     */
    private static final String GOODID = "goodid";

    @Override
    protected void handleModel(ServletRequestWrapper request) throws AccessDeniedException {
        int goodId = Integer.parseInt(request.getParameter(GOODID));

        userSession.removeFromCart(goodId);
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.CONTENT_PAGE.toString();
    }

}
