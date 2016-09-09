/*
 * PageConfigurationManager.java 19.01.2016
 */
package controller.servlet;

import java.util.ResourceBundle;

/**
 * Get the configuration for pages
 *
 * @author Kostiantyn Kovalchuk
 */
public class PageConfigurationManager {

    /**
     * Path to the configuration file
     */
    private static final String CONFIG_FILE = "controller.servlet.pages";
    
    /**
     * Resource bundle associated with the file
     */
    private static ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_FILE);
    
    
    public static String getProperty(String path) {
        return bundle.getString(path);
    }
}
