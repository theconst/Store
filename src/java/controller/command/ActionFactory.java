/*
 * ActionFactory.java 6.01.2016
 */
package controller.command;

import static controller.servlet.ServletConstants.ADDTOBLACKLIST;
import static controller.servlet.ServletConstants.ADDTOCART;
import static controller.servlet.ServletConstants.LOGIN;
import static controller.servlet.ServletConstants.LOGOUT;
import static controller.servlet.ServletConstants.REMOVEFROMBLACKLIST;
import static controller.servlet.ServletConstants.REMOVEFROMCART;
import static controller.servlet.ServletConstants.SUBMITORDER;
import static controller.servlet.ServletConstants.VIEWBLACKLIST;
import static controller.servlet.ServletConstants.VIEWCUSTOMERLIST;
import static controller.servlet.ServletConstants.VIEWSHOPCONTENT;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * Action factory - creates the instances of the object
 *
 * @author Kostiantyn Kovalchuk
 */
public class ActionFactory {

    /**
     * Instance of the factory
     */
    private static ActionFactory instance = new ActionFactory();

    /**
     * Maps user actions to commands
     */
    private HashMap<String, ActionCommand> commands = new HashMap();

    /**
     * Login
     */
    private ActionCommand loginCommand = new LoginCommand();

    /**
     * Logout
     */
    private ActionCommand logoutCommand = new LogoutCommand();

    /**
     * View customerList Command
     */
    private ActionCommand viewCustomerlistCommand = new ViewCustomerListCommand();

    /**
     * View the blacklist
     */
    private ActionCommand viewBlacklistCommand = new ViewBlacklistCommand();

    /**
     * Add to cart Command
     */
    private ActionCommand addToCartCommand = new AddToCartCommand();

    /**
     * Remove from cart command
     */
    private ActionCommand removeFromCartCommand = new RemoveFromCartCommand();

    /**
     * Submit order command
     */
    private ActionCommand submitOrderCommand = new SubmitOrderCommand();

    /**
     * Add to blacklist command
     */
    private ActionCommand addToBlacklistCommand = new AddToBlacklistCommand();

    /**
     * Remove from blacklist command
     */
    private ActionCommand removeFromBlacklistCommand = new RemoveFromBlacklistCommand();

    /**
     * Empty command
     */
    private ActionCommand emptyCommand = new EmptyCommand();

    /**
     * View the content command
     */
    private ActionCommand viewContentCommand = new CreateShopOrderCommand();

    private ActionFactory() {
        commands.put(LOGIN, loginCommand);
        commands.put(LOGOUT, logoutCommand);
        commands.put(ADDTOCART, addToCartCommand);
        commands.put(REMOVEFROMCART, removeFromCartCommand);
        commands.put(SUBMITORDER, submitOrderCommand);
        commands.put(ADDTOBLACKLIST, addToBlacklistCommand);
        commands.put(REMOVEFROMBLACKLIST, removeFromBlacklistCommand);
        commands.put(VIEWSHOPCONTENT, viewContentCommand);
        commands.put(VIEWCUSTOMERLIST, viewCustomerlistCommand);
        commands.put(VIEWBLACKLIST, viewBlacklistCommand);
    }

    /**
     * Gets command according to the servlet path
     *
     * @param request http request
     * @return
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getServletPath();
        ActionCommand result = commands.get(action);

        if (result == null) {
            return emptyCommand;
        }
        return result;
    }

    /**
     * Gets instance of the factory
     *
     * @return factory instance
     */
    public static ActionFactory getInstance() {
        return instance;
    }
}
