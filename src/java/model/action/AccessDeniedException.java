/*
 * AccessDeniedException.java
 */
package model.action;

/**
 * Exception that is thrown if user tries to access the content
 * 
 * @author Kostiantyn Kovalchuk
 */
public class AccessDeniedException extends Throwable {

    public AccessDeniedException(String message) {
        super(message);
    }
    
}
