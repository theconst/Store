/*
 * View the shopcontent
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import model.action.AccessDeniedException;

/**
 * Views the content of the shop
 * 
 * @author Kostiantyn Kovalchuk
 */
public class CreateShopOrderCommand extends ActionCommand {

    @Override
    protected void handleModel(ServletRequestWrapper request) throws AccessDeniedException {
        userSession.createOrder();
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.CONTENT_PAGE.toString();
    }

}
