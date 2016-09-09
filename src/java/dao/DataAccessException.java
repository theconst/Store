/*
 * DataAccessException 18.01.2016
 */
package dao;

/**
 * Represents a data access exception
 *
 * @author Kostiantyn Kovalchuk
 */
public class DataAccessException extends Exception {

    /**
     * Constructs the exception with the specified reason
     *
     * @param reason reason of the exception
     */
    public DataAccessException(Exception reason) {
        super(reason);
    }

    /**
     * Creates the exception and sets the message
     *
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }
}
