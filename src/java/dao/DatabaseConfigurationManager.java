/*
 * Database configuration manager
 */
package dao;

import java.util.ResourceBundle;

/**
 * Manages the properties of the database
 * 
 * @author Kostiantyn Kovalchuk
 */
public class DatabaseConfigurationManager {
    
    private static final String CONFIG_FILE = "dao.database";
    
    private static ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_FILE);
    
    public static String getProperty(String path) {
        return bundle.getString(path);
    }
    
}
