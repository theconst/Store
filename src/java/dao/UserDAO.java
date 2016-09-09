/*
 * MySQLAccountDAO.java
 */
package dao;

import static dao.DatabaseConstants.DATABASE_NAME;
import static dao.DatabaseConstants.USER_TBL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.User;

/**
 * Access the account table from the database
 *
 * @author Kostiantyn Kovalchuk
 */
public class UserDAO extends AbstractOneTableDAO<Integer, User> {

    /**
     * User's id
     */
    private static final String ID_COL = "id";
    
    /**
     * User's login
     */
    private static final String LOGIN_COL = "login";
    
    /**
     * User's password
     */
    private static final String PASSWORD_COL = "password";
    
    /**
     * User's permission
     */
    private static final String PERMISSION_COL = "permission";

    
    /**
     * Number of columns in the user's table
     */
    private static final int COL_COUNT = 4;
    
    /**
     * Number of the user's id in the table
     */
    private static final int ID_COL_NUM = 1;
    
    /**
     * Number of the login in the table
     */
    private static final int LOGIN_COL_NUM = 2;
    
    /**
     * Number of password column in the table
     */
    private static final int PASSWORD_COL_NUM = 3;
    
    /**
     * Number of the permission column in the table
     */
    private static final int PERMISSION_COL_NUM = 4;

    /**
     * Selects all users
     */
    private static final String SELECT_ALL
            = QueryCreator.createSelectAll(DATABASE_NAME, USER_TBL);
    
    /**
     * Selects user with the specified key
     */
    private static final String SELECT_BY_KEY
            = QueryCreator.createSelectByKey(DATABASE_NAME, USER_TBL, ID_COL);
    
    /**
     * Selects user with the specified login
     */
    private static final String SELECT_BY_LOGIN
            = QueryCreator.createSelectByKey(DATABASE_NAME, USER_TBL, LOGIN_COL);
    
    /**
     * Inserts new entries in the table
     */
    private static final String INSERT
            = QueryCreator.createInsert(DATABASE_NAME, USER_TBL, COL_COUNT);
    
    /**
     * Deletes entries from the table
     */
    private static final String DELETE
            = QueryCreator.createDelete(DATABASE_NAME, USER_TBL, ID_COL);
    
    /**
     * Inserts user with the default key
     */
    private static final String INSERT_DEFAULT
            = QueryCreator.createInsert(DATABASE_NAME, USER_TBL, COL_COUNT);

    public UserDAO(Connection connection) throws DataAccessException {
        super(connection);
    }

    @Override
    protected User buildEntity(ResultSet rs) throws SQLException {
        User user = new User();
        int id = rs.getInt(ID_COL);
        String login = rs.getString(LOGIN_COL);
        String password = rs.getString(PASSWORD_COL);
        String permission = rs.getString(PERMISSION_COL);

        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setPermission(permission);
        return user;
    }

    @Override
    protected PreparedStatement prepareFindByKeyQuery(Integer key) throws SQLException {
        PreparedStatement findByKey = connection.prepareStatement(SELECT_BY_KEY);

        findByKey.setInt(ID_COL_NUM, key);
        return findByKey;
    }

    @Override
    protected PreparedStatement prepareFindAllQuery() throws SQLException {
        PreparedStatement findAll = connection.prepareStatement(SELECT_ALL);

        return findAll;
    }

    @Override
    protected PreparedStatement prepareInsertQuery(User entity) throws SQLException {
        PreparedStatement insert = connection.prepareStatement(INSERT);

        insert.setInt(ID_COL_NUM, entity.getId());
        insert.setString(LOGIN_COL_NUM, entity.getLogin());
        insert.setString(PASSWORD_COL_NUM, entity.getPassword());
        insert.setString(PERMISSION_COL_NUM, entity.getPermission());
        return insert;
    }

    @Override
    public Integer create(User entity, boolean setDefaultKey)
            throws DataAccessException {
        if (!setDefaultKey) {
            return create(entity);
        }
        try {
            PreparedStatement insertDefault
                    = connection.prepareStatement(INSERT_DEFAULT);

            insertDefault.setString(LOGIN_COL_NUM - 1, entity.getLogin());
            insertDefault.setString(PASSWORD_COL_NUM - 1, entity.getPassword());
            insertDefault.setString(PERMISSION_COL_NUM - 1, entity.getPermission());

            ResultSet rs = insertDefault.executeQuery();
            rs.next();
            return buildEntity(rs).getKey();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected PreparedStatement prepareDeleteQuery(Integer key)
            throws SQLException {
        PreparedStatement delete = connection.prepareStatement(DELETE);

        delete.setInt(1, key);
        return delete;
    }

    
    /**
     * Gets the user by login
     * 
     * @param login user's login
     * @return user or null if not found
     * @throws DataAccessException  if some error occurred
     */
    public User getByLogin(String login) throws DataAccessException {
        try {
            PreparedStatement getByLogin
                    = connection.prepareStatement(SELECT_BY_LOGIN);
            ResultSet rs;
            getByLogin.setString(1, login);
            rs = getByLogin.executeQuery();
            
            if (rs.next()) {
                return buildEntity(rs);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }
}
