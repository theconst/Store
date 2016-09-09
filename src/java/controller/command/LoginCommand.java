/*
 * LoginCommand.java
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import dao.DataAccessException;

/**
 * Logins the user to the system
 *
 * @author Kostiantyn Kovalchuk
 */
public class LoginCommand extends ActionCommand {

    /**
     * User's login
     */
    private static final String LOGIN = "login";

    /**
     * User's password
     */
    private static final String PASSWORD = "password";

    /**
     * Error message
     */
    private static final String ERROR_MESSAGE = "errorMessage";

    /**
     * Error message text
     */
    private static final String ERROR_MESSAGE_TEXT
            = "You have entered wrong username/password";

    @Override
    protected void handleModel(ServletRequestWrapper request) throws DataAccessException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        userSession.login(login, password);
    }

    @Override
    protected String handleView(ServletRequestWrapper request) {
        if (userSession.getUser() == null) {
            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            return Page.LOGIN_PAGE.toString();
        }
        if (userSession.isAdmin()) {
            return Page.ADMINISTRATOR_PANEL_PAGE.toString();
        }
        return Page.WELCOME_PAGE.toString();
    }

}
