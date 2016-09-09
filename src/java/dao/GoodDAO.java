/*
 * GoodDAO.java 5.11.2016
 */
package dao;

import model.entity.Good;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DatabaseConstants.DATABASE_NAME;
import static dao.DatabaseConstants.GOOD_TBL;

/**
 * Gets good from the underlying database
 *
 * @author Kostiantyn Kovalchuk
 */
public class GoodDAO extends AbstractOneTableDAO<Integer, Good> {

    /**
     * Id of the good
     */
    private static final String ID_COL = "id";
    
    /** 
     * Name of the good
     */
    private static final String NAME_COL = "name";
    
    /**
     * Price of the good
     */
    private static final String PRICE_COL = "price";
    
    /**
     * Type of the good
     */
    private static final String TYPE_COL = "type";
    
    /**
     * Description of the good
     */
    private static final String DESCRIPTION_COL = "description";

    /**
     * Number of columns of the table
     */
    private static final int COL_COUNT = 5;
    
    /**
     * Number of the id column in the table
     */
    private static final int ID_COL_NUM = 1;
    
    /**
     * Number of the name column in the table
     */
    private static final int NAME_COL_NUM = 2;
    
    /**
     * Number of the price column in the table
     */
    private static final int PRICE_COL_NUM = 3;
    
    /**
     * Number of the description column in the table
     */
    private static final int DESCRIPTION_COL_NUM = 4;
    
    /**
     * Number of the type column in the table
     */
    private static final int TYPE_COL_NUM = 5;

    
    /**
     * Select all statement
     */
    private static final String SELECT_ALL
            = QueryCreator.createSelectAll(DATABASE_NAME, GOOD_TBL);
    
    /**
     * Select by key statement
     */
    private static final String SELECT_BY_KEY
            = QueryCreator.createSelectByKey(DATABASE_NAME, GOOD_TBL,
                    ID_COL);
    
    /**
     * Insert statement
     */
    private static final String INSERT
            = QueryCreator.createInsert(DATABASE_NAME, GOOD_TBL, COL_COUNT);

    
    /**
     * Delete statement
     */
    private static final String DELETE
            = QueryCreator.createDelete(DATABASE_NAME, GOOD_TBL, ID_COL);

    public GoodDAO(Connection connection) throws DataAccessException {
        super(connection);
    }

    @Override
    protected Good buildEntity(ResultSet rs) throws SQLException {
        Good good = new Good();
        int id = rs.getInt(ID_COL);
        int price = rs.getInt(PRICE_COL);
        String name = rs.getString(NAME_COL);
        String description = rs.getString(DESCRIPTION_COL);
        String type = rs.getString(TYPE_COL);

        good.setId(id);
        good.setPrice(price);
        good.setType(type);
        good.setDescription(description);
        good.setName(name);
        return good;
    }

    @Override
    protected PreparedStatement prepareFindByKeyQuery(Integer key) throws SQLException {
        PreparedStatement findByKey = connection.prepareStatement(SELECT_BY_KEY);

        findByKey.setInt(ID_COL_NUM, key);
        return findByKey;
    }

    @Override
    protected PreparedStatement prepareFindAllQuery() throws SQLException {
        return connection.prepareStatement(SELECT_ALL);
    }

    @Override
    protected PreparedStatement prepareInsertQuery(Good entity) throws SQLException {
        PreparedStatement insert = connection.prepareStatement(INSERT);

        insert.setInt(ID_COL_NUM, entity.getId());
        insert.setInt(PRICE_COL_NUM, entity.getPrice());
        insert.setString(NAME_COL_NUM, entity.getName());
        insert.setString(DESCRIPTION_COL_NUM, entity.getDescription());
        insert.setString(TYPE_COL_NUM, entity.getType());
        return insert;
    }

    @Override
    protected PreparedStatement prepareDeleteQuery(Integer key) throws SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE);
        deleteStatement.setInt(1, key);

        return deleteStatement;
    }
    
}
