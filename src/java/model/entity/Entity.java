/*
 * Entity.java 10.06.2016 
 */
package model.entity;

/**
 * Abstract class for item in a persistent storage
 *
 * @author Kostiantyn Kovalchuk
 * @param <K> Type of the entity's key
 */
public interface Entity<K> {

    /* Get key of the entity
       Key - some value that uniquely determines entity */
    K getKey();
}
