/*
 * ActionCommand.java 6.01.2016
 */
package controller.command;

import view.ContentViewAdapter;
import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;
import dao.DataAccessException;
import javax.servlet.http.HttpSession;
import model.action.AccessDeniedException;
import model.action.UserSession;
import view.SessionInfoViewAdapter;

/**
 * Represents an action in an application
 *
 * @author Kostiantyn Kovalchuk
 */
public abstract class ActionCommand {

    /**
     * Session attribute storing the information about session
     */
    protected static final String SESSION = "sessionInfo";

    /**
     * Content property storing the information about content
     */
    protected static final String CONTENT = "content";

    /**
     * User session associated with the user
     */
    protected UserSession userSession;

    /**
     * Htpp session of the user
     */
    protected HttpSession session;

    /**
     * Adapter for the viewing needed session info on the page
     */
    protected SessionInfoViewAdapter sessionInfo;

    /**
     * Executes the command. Delegates the actions on the view and model to its
     * subclasses
     *
     * @param request user's request
     * @return the URL of the JSP with the result of execution
     */
    public final String execute(ServletRequestWrapper request) {
        ContentViewAdapter viewer = ContentViewAdapter.getInstance();

        session = request.getSession();
        sessionInfo = (SessionInfoViewAdapter) session.getAttribute(SESSION);
        if (sessionInfo == null) {
            userSession = UserSession.getSession();
            sessionInfo = SessionInfoViewAdapter.getAdapter(userSession);
            session.setAttribute(SESSION, sessionInfo);
        }
        userSession = sessionInfo.getUserSession();
        try {
            handleModel(request);
        } catch (AccessDeniedException ex) {
            return Page.ACESS_DENIED_PAGE.toString();
        } catch (DataAccessException ex) {
            return Page.ERROR_PAGE.toString();
        }

        request.setAttribute(CONTENT, viewer);
        return handleView(request);
    }

    /**
     * Fragment of the template method that handles the action on the model
     *
     * @param request user's request
     * @throws AccessDeniedException
     * @throws dao.DataAccessException
     */
    protected void handleModel(ServletRequestWrapper request)
            throws AccessDeniedException, DataAccessException {
        //no action by default
    }

    /**
     * Returns the user's page
     *
     * @param request user's request
     * @return corresponding jsp
     */
    protected abstract String handleView(ServletRequestWrapper request);
}
