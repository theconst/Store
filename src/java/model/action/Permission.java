/*
 * Permission.java 19.01.2016
 */
package model.action;

/**
 * Permissions in the system
 * 
 * @author Kostiantyn Kovalchuk
 */
public enum Permission {
    
    
    
    /**
     * Administrator permision and its string value
     */
    ADMIN("admin"), 
    
    /**
     * Customer permission and its string value
     */
    CUSTOMER("customer"), 
    
    /**
     * Unauthorized permission and its string value
     */
    UNAUTORIZED(""), 
    
    /**
     * Unauthenticated permission and its string value
     */
    UNAUTHENTICATED("");
    
    /**
     * String representation of the permission
     */
    private String value;
    
    
    /**
     * Constructs the permission and its corresponding string value
     * 
     * @param value string value of the permission
     */
    Permission(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    /**
     * Returns the permission value from its string representation
     * @param string string
     * @return permission / unauthenticated by default
     */
    public static Permission valueFrom(String string) {
        for (Permission p : Permission.values()) {
            if (p.toString().equals(string)) {
                return p;
            }
        }
        return UNAUTHENTICATED;
    }
}
