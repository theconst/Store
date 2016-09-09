/*
 * EmptyCommand.java
 */
package controller.command;

import controller.servlet.Page;
import controller.servlet.ServletRequestWrapper;

/**
 * Command that does nothing and is executed when no action is found
 * 
 * @author Kostiantyn Kovalchuk
 */
class EmptyCommand extends ActionCommand {

    @Override
    protected String handleView(ServletRequestWrapper request) {
        return Page.WELCOME_PAGE.toString();
    }    
    
}
