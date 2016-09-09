/*
 * CustomerDAO.java 5.01.2016
 */
package dao;

import static dao.DatabaseConstants.BLACKLIST_TBL;
import static dao.DatabaseConstants.CUSTOMER_TBL;
import static dao.DatabaseConstants.CUSTOMER_VIEW;
import static dao.DatabaseConstants.DATABASE_NAME;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.entity.*;

/**
 * Selects entity from Customer_view , but puts it to Customer table
 *
 * @author Kostiantyn Kovalchuk
 */
public class CustomerDAO extends AbstractDAO<Integer, Customer> {

    /**
     * Id of the customer column
     */
    private static final String ID_COL = "id";

    /**
     * Name of the customer column
     */
    private static final String NAME_COL = "name";

    /**
     * Address column
     */
    private static final String ADDRESS_COL = "address";

    /**
     * Column that checks if customer is in blacklist
     */
    private static final String IS_BLACKLISTED_COL = "is_blacklisted";

    /**
     * Id ot the customer column
     */
    private static final String CUSTOMER_ID_COL = "customer_id";

    /**
     * Number of the columns in the customer table
     */
    private static final int COL_COUNT = 3;

    /**
     * Number of id column in the full table
     */
    private static final int ID_COL_NUM = 1;

    /**
     * Number of the name column in the full table
     */
    private static final int NAME_COL_NUM = 2;

    /**
     * Number of the address column in the full table
     */
    private static final int ADDRESS_COL_NUM = 3;

    /**
     * Selects all entries from the customer view
     */
    private static final String SELECT_ALL
            = QueryCreator.createSelectAll(DATABASE_NAME, CUSTOMER_VIEW);

    /**
     * Selects customer by key from the view
     */
    private static final String SELECT_BY_KEY
            = QueryCreator.createSelectByKey(DATABASE_NAME, CUSTOMER_VIEW,
                    ID_COL);

    /**
     * Inserts into the customer table
     */
    private static final String INSERT
            = QueryCreator.createInsert(DATABASE_NAME, CUSTOMER_TBL, COL_COUNT);

    /**
     * Deletes from the customer table
     */
    private static final String DELETE
            = QueryCreator.createDelete(DATABASE_NAME, CUSTOMER_TBL, ID_COL);

    /**
     * Query that adds customer to the blacklist
     */
    private static final String ADD_TO_BLACKLIST
            = QueryCreator.createInsert(DATABASE_NAME, BLACKLIST_TBL, 1);

    /**
     * Query that removes customer from the blacklist
     */
    private static final String REMOVE_FROM_BLACKLIST
            = QueryCreator.createDelete(DATABASE_NAME, BLACKLIST_TBL, CUSTOMER_ID_COL);

    /**
     * Inner that selects the part of the customer stored in Customer table
     */
    private CustomerPartDAO innerDAO;

    /**
     * DAO that selects from the user table
     */
    private UserDAO userDAO;

    /**
     * Initializes all needed DAO's for the customer
     *
     * @param connection JDBC connection
     * @throws DataAccessException if initialization fails
     */
    public CustomerDAO(Connection connection) throws DataAccessException {
        super(connection);
        innerDAO = new CustomerPartDAO(connection);
        userDAO = new UserDAO(connection);
    }

    /**
     * Part of the customer stored in customer table
     */
    private class CustomerPart implements Entity<Integer> {

        public int id;
        public String name;
        public String address;
        public String isBlacklisted;

        @Override
        public Integer getKey() {
            return id;
        }
    }

    /**
     * DAO for the customer part
     */
    private class CustomerPartDAO extends AbstractOneTableDAO<Integer, CustomerPart> {

        public CustomerPartDAO(Connection connection) throws DataAccessException {
            super(connection);
        }

        @Override
        protected CustomerPart buildEntity(ResultSet rs) throws SQLException {
            CustomerPart part = new CustomerPart();
            part.id = rs.getInt(ID_COL);
            part.name = rs.getString(NAME_COL);
            part.address = rs.getString(ADDRESS_COL);
            part.isBlacklisted = rs.getString(IS_BLACKLISTED_COL);

            return part;
        }

        @Override
        protected PreparedStatement prepareFindByKeyQuery(Integer key)
                throws SQLException {
            PreparedStatement findByKey = connection.prepareStatement(SELECT_BY_KEY);

            findByKey.setInt(ID_COL_NUM, key);
            return findByKey;
        }

        @Override
        protected PreparedStatement prepareFindAllQuery() throws SQLException {
            return connection.prepareStatement(SELECT_ALL);
        }

        @Override
        protected PreparedStatement prepareInsertQuery(CustomerPart entity) throws SQLException {
            PreparedStatement insert = connection.prepareStatement(INSERT);

            insert.setInt(ID_COL_NUM, entity.id);
            insert.setString(NAME_COL_NUM, entity.name);
            insert.setString(ADDRESS_COL_NUM, entity.address);
            return insert;
        }

        @Override
        protected PreparedStatement prepareDeleteQuery(Integer key) throws SQLException {
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE);
            deleteStatement.setInt(1, key);

            return deleteStatement;
        }
    }

    /**
     * Appends information from part to customer
     *
     * @param customer
     * @param part
     * @return
     */
    private Customer merge(Customer customer, CustomerPart part) {
        boolean isBlacklisted = Boolean.parseBoolean(part.isBlacklisted);

        customer.setInBlackList(isBlacklisted);
        customer.setAddress(part.address);
        customer.setName(part.name);

        return customer;
    }

    @Override
    public List<Customer> findAll() throws DataAccessException {
        List<Customer> result = new ArrayList<>();

        for (CustomerPart part : innerDAO.findAll()) {
            User user = userDAO.find(part.id);
            Customer customer = new Customer(user);
            customer = merge(customer, part);
            result.add(customer);
        }
        return result;
    }

    @Override
    public Customer find(Integer key) throws DataAccessException {
        User user = userDAO.find(key);
        Customer customer = new Customer(user);
        CustomerPart customerPart = innerDAO.find(key);

        customer = merge(customer, customerPart);
        return customer;
    }

    public Customer find(User user) throws DataAccessException {
        Customer customer = new Customer(user);
        CustomerPart customerPart = innerDAO.find(user.getKey());

        customer = merge(customer, customerPart);
        return customer;
    }

    @Override
    public void delete(Integer key) throws DataAccessException {
        innerDAO.delete(key);
        userDAO.delete(key);
    }

    /**
     * Adds the specified customer to the blacklist
     *
     * @param customerId customer's id
     * @throws DataAccessException
     */
    public void addToBlacklist(int customerId) throws DataAccessException {
        try {
            PreparedStatement addToBlacklist
                    = connection.prepareStatement(ADD_TO_BLACKLIST);
            addToBlacklist.setInt(1, customerId);
            addToBlacklist.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    /**
     * Removes the customer from the blacklist
     *
     * @param customerId
     * @throws DataAccessException
     */
    public void removeFromBlacklist(int customerId) throws DataAccessException {
        try {
            PreparedStatement removeFromBlacklist
                    = connection.prepareStatement(REMOVE_FROM_BLACKLIST);
            removeFromBlacklist.setInt(1, customerId);
            removeFromBlacklist.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Integer create(Customer entity) throws DataAccessException {
        int customerId = entity.getId();
        CustomerPart part = new CustomerPart();

        if (userDAO.find(customerId) == null) {
            userDAO.create(entity);
        }
        part.address = entity.getAddress();
        part.id = entity.getId();
        part.name = entity.getName();
        part.isBlacklisted = ((Boolean) entity.isInBlackList()).toString();
        innerDAO.create(part);
        if (entity.isInBlackList()) {
            addToBlacklist(customerId);
        } else {
            removeFromBlacklist(customerId);
        }
        return customerId;
    }

    @Override
    public Customer update(Customer entity) throws DataAccessException {
        Customer result = find(entity);
        delete(entity);
        create(entity);
        return result;
    }
}
