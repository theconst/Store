/*
 * AbstractOneTableDAO.java 4.01.2016
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.entity.Entity;

/**
 * Abstract DAO defines the common DAO for accessing data from one table
 *
 * @author Kostiantyn Kovalchuk
 * @param <T> type of the entity
 * @param <K> type of the key
 */
public abstract class AbstractOneTableDAO<K, T extends Entity<K>> extends AbstractDAO<K, T> {

    /**
     * Constructs DAO with the corresponding connection Connection should be
     * closed outside and is never closed here
     *
     * @param connection connection to the database
     * @throws dao.DataAccessException if data access error occurred
     */
    public AbstractOneTableDAO(Connection connection) throws DataAccessException {
        super(connection);
    }

    /**
     * Fragment of template method which handles the result set and gets entity
     * from it
     *
     * @param rs result set of a query
     * @return entity obtained from the result set
     * @throws java.sql.SQLException if error in accessing the database
     */
    protected abstract T buildEntity(ResultSet rs) throws SQLException;

    /**
     * Fragment of the template method Gets the find by key query
     *
     * @param key key of the entity
     * @return statement that finds the entity by its key
     * @throws java.sql.SQLException
     */
    protected abstract PreparedStatement prepareFindByKeyQuery(K key) throws SQLException;

    /**
     * Fragment of the template method Prepares the find all query
     *
     * @return the query prepared for the find all entries
     * @throws java.sql.SQLException if error accessing the database or data
     * cannot be processed
     */
    protected abstract PreparedStatement prepareFindAllQuery() throws SQLException;

    /**
     * Fragment of the template method Prepares the insert query
     *
     * @param entity entity to be inserted
     * @return the query prepared for the insertion of the entity;
     * @throws java.sql.SQLException if error accessing the database or data
     * cannot be processed
     */
    protected abstract PreparedStatement prepareInsertQuery(T entity) throws SQLException;

    /**
     * Fragment of the template method - deletes the entry by key
     *
     * @param key
     * @return
     * @throws java.sql.SQLException
     */
    protected abstract PreparedStatement prepareDeleteQuery(K key)
            throws SQLException;

    /**
     * Find all the entities in the source
     *
     * @return all the entities from the source, null if no such entries
     * @throws dao.DataAccessException if error accessing the data
     */
    @Override
    public final List<T> findAll() throws DataAccessException {
        PreparedStatement findByKey;
        List<T> allEntities;
        T entity;

        try {
            findByKey = prepareFindAllQuery();
            ResultSet resultSet = findByKey.executeQuery();
            allEntities = new ArrayList<>();
            while (resultSet.next()) {
                entity = buildEntity(resultSet);
                allEntities.add(entity);
            }
            return allEntities;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Template method which finds entity by key
     *
     * @param key key value to find
     * @return entity if it is found, null otherwise
     * @throws dao.DataAccessException if error accessing the data
     */
    @Override
    public final T find(K key) throws DataAccessException {
        PreparedStatement findByKey;

        try {
            findByKey = prepareFindByKeyQuery(key);
            ResultSet resultSet = findByKey.executeQuery();
            if (resultSet.next()) {
                return buildEntity(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return null;
    }

    /**
     * Template method which deletes entity by its key
     *
     * @param key key of the entity
     * @throws dao.DataAccessException if error accessing the data
     */
    @Override
    public final void delete(K key) throws DataAccessException {
        PreparedStatement deleteStatement;

        try {
            deleteStatement = prepareDeleteQuery(key);
            deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Template method Creates the corresponding entries in database
     *
     * @param entity entity to create
     * @return key of the created entity
     * @throws dao.DataAccessException
     */
    @Override
    public final K create(T entity) throws DataAccessException {
        PreparedStatement insertStatement;

        try {
            insertStatement = prepareInsertQuery(entity);
            insertStatement.execute();
            return entity.getKey();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Template method Updates the entity
     *
     * @param entity entity to update
     * @return old entry
     * @throws dao.DataAccessException if error in creating the entry
     */
    @Override
    public final T update(T entity) throws DataAccessException {
        T oldEntry;

        oldEntry = find(entity.getKey());
        delete(entity.getKey());
        create(entity);
        return oldEntry;
    }
}
