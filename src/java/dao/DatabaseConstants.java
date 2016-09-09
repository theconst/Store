/*
 * DatabaseConstants.java 4.01.2016
 */
package dao;

/**
 * Stores the constants, specific for the database: name of the database and
 * underlying tables
 *
 * @author Kostiantyn Kovalchuk
 */
public final class DatabaseConstants {

    /**
     * Name of the database
     */
    public static final String DATABASE_NAME 
            = DatabaseConfigurationManager.getProperty("database.name");
    
    /**
     * Good table
     */
    public static final String GOOD_TBL = "Good";
    
    /**
     * Customer table
     */
    public static final String CUSTOMER_TBL = "Customer";
    
    /**
     * Shopping cart table
     */
    public static final String SHOPPING_CART_TBL = "Shopping_cart";
    
    /**
     * Purchase order table
     */
    public static final String PURCHASE_ORDER_TBL = "Purchase_order";
    
    /**
     * Shopping cart view table
     */
    public static final String SHOPPING_CART_VIEW = "Shopping_cart_view";
    
    /**
     * Shopping cart total table
     */
    public static final String SHOPPING_CART_TOTAL_VIEW = "Shopping_cart_total_view";
    
    /**
     * Customer view
     */
    public static final String CUSTOMER_VIEW = "Customer_view";
    
    /**
     * User table
     */
    public static final String USER_TBL = "User";
    
    /**
     * Blacklist table
     */
    public static final String BLACKLIST_TBL = "Blacklist";

    private DatabaseConstants() {}
}
