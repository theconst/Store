/*
 * AddToCartCommand.java 19.01.2016
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import model.action.AccessDeniedException;

/**
 * Add the specified good to the cart
 *
 * @author Kostiantyn Kovalchuk
 */
public class AddToCartCommand extends ActionCommand {

    /**
     * Id of the good - user's choice
     */
    private static final String GOODID = "goodid";

    @Override
    protected void handleModel(ServletRequestWrapper request) 
            throws AccessDeniedException {
        int goodId = Integer.parseInt(request.getParameter(GOODID));

        userSession.addToCart(goodId);
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.CONTENT_PAGE.toString();
    }

}
