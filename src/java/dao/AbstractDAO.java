/*
 * AbstractDAO.java 5.01.2016
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.entity.Entity;

/**
 * Provides the common interface for all DAOs.
 * Operations are never committed automatically.
 *
 * @author Kostiantyn Kovalchuk
 * @param <K> KEY of the entity
 * @param <T> Type of the entity
 */
public abstract class AbstractDAO<K, T extends Entity<K>> {

    /**
     * Creates the entity with the default key if supported is false by default
     */
    public static final boolean USE_CUSTOM_KEY = false;

    /**
     * Use custom key for creating the entity
     */
    public static final boolean USE_DEFAULT_KEY = true;

    /**
     * Connection to the underlying database
     */
    protected Connection connection;

    /**
     * Constructs DAO with the corresponding connection Connection should be
     * closed outside and is never closed here
     *
     * @param connection connection to the database
     * @throws dao.DataAccessException if data access error occurred
     */
    public AbstractDAO(Connection connection) throws DataAccessException {
        try {
            connection.setAutoCommit(false);
            this.connection = connection;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Find all the entities in the source
     *
     * @return all the entities from the source, null if no such entries
     * @throws dao.DataAccessException if data access error occurred
     */
    public abstract List<T> findAll() throws DataAccessException;

    /**
     * Finds entity by key
     *
     * @param key key value to find
     * @return entity if it is found, null otherwise
     * @throws dao.DataAccessException if data access error occurred
     */
    public abstract T find(K key) throws DataAccessException;

    /**
     * Deletes the entity
     *
     * @param key
     * @throws dao.DataAccessException
     */
    public abstract void delete(K key) throws DataAccessException;

    public void delete(T entity) throws DataAccessException {
        delete(entity.getKey());
    }

    /**
     * Template method Creates the corresponding entries in database
     *
     * @param entity entity to create
     * @return key of the created entity
     * @throws dao.DataAccessException if data access error occurred
     */
    public abstract K create(T entity) throws DataAccessException;

    /**
     * Creates entity with default key if setDefaultKey is true Operation is not
     * supported by default
     *
     * @param entity entity to be created
     * @param setDefaultKey
     * @return
     * @throws DataAccessException
     */
    public K create(T entity, boolean setDefaultKey) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates the entity
     *
     * @param entity entity to update
     * @return old value
     * @throws dao.DataAccessException if data access error occurred
     */
    public abstract T update(T entity) throws DataAccessException;
    
}
