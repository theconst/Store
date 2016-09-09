/*
 * Page.java 
 */
package controller.servlet;

/**
 * Page in the application
 *
 * @author Kostiantyn Kovalchuk
 */
public enum Page {

    /**
     * Welcome page 
     */
    WELCOME_PAGE("path.page.index"),
    
    /**
     * Login page
     */
    LOGIN_PAGE("path.page.login"),
    
    /**
     * Administrator panel page
     */
    ADMINISTRATOR_PANEL_PAGE("path.page.adminpanel"),
    
    /**
     * Blacklist page
     */
    BLACKLIST_PAGE("path.page.blacklist"),
    
    /**
     * Confirmation page
     */
    CONFIRMATION_PAGE("path.page.confirmation"),
    
    /**
     * Page with shop content
     */
    CONTENT_PAGE("path.page.shopcontent"),
    
    /** 
     * Page with list of customers
     */
    CUSTOMERLIST_PAGE("path.page.customerlist"),
    
    /**
     * Error page
     */
    ERROR_PAGE("path.page.errorpage"),
    
    /**
     * Page with access denial
     */
    ACESS_DENIED_PAGE("path.page.accessdenied");

    
    /**
     * Name of the page
     */
    private String pageName;

    /**
     * Gets the property of the page and assigns it to pageName
     *
     * @param path path to property in the bundle file
     */
    Page(String path) {
        pageName = PageConfigurationManager.getProperty(path);
    }

    @Override
    public String toString() {
        return pageName;
    }
}
