/*
 * ShoppingCartDAO.java 5.01.2016
 */
package dao;

import model.entity.ShoppingCart;
import model.entity.ShoppingCart.ShoppingCartEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static dao.DatabaseConstants.DATABASE_NAME;
import static dao.DatabaseConstants.SHOPPING_CART_TBL;
import java.sql.Statement;

/**
 * Get the shopping cart from the underlying database
 *
 * @author Kostiantyn Kovalchuk
 */
public class ShoppingCartDAO extends AbstractDAO<Integer, ShoppingCart> {

    /**
     * id column of the table
     */
    private static final String CART_ID_COL = "id";
    
    /**
     * Good id column of the table
     */
    private static final String GOOD_ID_COL = "good_id";
    
    /**
     * Count of good column
     */
    private static final String COUNT_OF_GOOD_COL = "count_of_good";
    
    /**
     * Statement for insertion of the table with default key
     */
    private static final String INSERT_DEFAULT = "INSERT INTO " + DATABASE_NAME + "."
            + SHOPPING_CART_TBL + "(" + GOOD_ID_COL + ", "
            + COUNT_OF_GOOD_COL + ")" + "VALUES(?, ?)";

    /**
     * Count of columns in table
     */
    private static final int COL_COUNT = 3;
    
    /**
     * Number of id column in the full table
     */
    private static final int CART_ID_COL_NUM = 1;
    
    /**
     * Number of good_id column in the full table
     */
    private static final int GOOD_ID_COL_NUM = 2;
    
    /**
     * Number of count of good int the full table
     */
    private static final int COUNT_OF_GOOD_COL_NUM = 3;

    /**
     * Selects entries by key
     */
    private static final String SELECT_BY_KEY
            = QueryCreator.createSelectByKey(DATABASE_NAME,
                    SHOPPING_CART_TBL, CART_ID_COL);

    /**
     * Selects only keys (id's) of the shopping cart
     */
    private static final String SELECT_KEY_ONLY
            = QueryCreator.createCustomSelect("SC.id",
                    "Store.Shopping_cart AS SC", "TRUE");

    /**
     * Inserts the full entry in the table
     */
    private static final String INSERT
            = QueryCreator.createInsert(DATABASE_NAME, SHOPPING_CART_TBL,
                    COL_COUNT);

    /**
     * Deletes entry from the table by id
     */
    private static final String DELETE
            = QueryCreator.createDelete(DATABASE_NAME,
                    SHOPPING_CART_TBL, CART_ID_COL);

    public ShoppingCartDAO(Connection connection) throws DataAccessException {
        super(connection);
    }

    @Override
    public List<ShoppingCart> findAll() throws DataAccessException {
        List<ShoppingCart> list = new ArrayList<>();

        try {
            PreparedStatement selectKeys = connection.prepareStatement(SELECT_KEY_ONLY);
            ResultSet foundKeys = selectKeys.executeQuery();

            while (foundKeys.next()) {
                int cartId = foundKeys.getInt(CART_ID_COL);
                ShoppingCart cart = find(cartId);
                list.add(cart);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return list;
    }

    @Override
    public ShoppingCart find(Integer key) throws DataAccessException {
        ShoppingCart shoppingCart = new ShoppingCart();
        List<ShoppingCartEntry> goods;

        try {
            PreparedStatement selectCart = connection.prepareStatement(
                    SELECT_BY_KEY);
            ResultSet entriesResultSet = selectCart.executeQuery();

            goods = handleResultSetEntries(entriesResultSet);

            shoppingCart.setId(key);
            shoppingCart.setGoods(goods);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return shoppingCart;
    }

    
    /**
     * Gets the goods correstponding to the specified shopping cart
     * 
     * @param rs result set from select query
     * @return shopping cart entries in the cart
     * @throws SQLException
     */
    private List<ShoppingCartEntry> handleResultSetEntries(ResultSet rs) throws SQLException {
        List<ShoppingCart.ShoppingCartEntry> goods = new ArrayList<>();

        while (rs.next()) {
            ShoppingCartEntry entry = new ShoppingCartEntry();

            entry.setGoodId(rs.getInt(GOOD_ID_COL));
            entry.setNumberOfItems(rs.getInt(COUNT_OF_GOOD_COL));
            goods.add(entry);
        }
        return goods;
    }

    @Override
    public void delete(Integer key) throws DataAccessException {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE);
            deleteStatement.executeQuery();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Integer create(ShoppingCart entity) throws DataAccessException {
        return create(entity, false);
    }

    @Override
    public Integer create(ShoppingCart shoppingCart, boolean setDefaultKey)
            throws DataAccessException {
        try {
            List<ShoppingCartEntry> list = shoppingCart.getGoods();
            int size = list.size();
            int cartId;

            /* create first entry of the shopping cart */
            if (setDefaultKey) {
                cartId = createEntry(list.get(0));
                shoppingCart.setId(cartId);
            } else {
                cartId = shoppingCart.getId();
                createEntry(list.get(0), cartId);
            }

            /* Create left entries of the shopping cart */
            for (int i = 1; i < size; ++i) {
                createEntry(list.get(i), cartId);
            }
            return shoppingCart.getKey();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    
    /**
     * Creates shopping cart and its entry with default keys
     * 
     * @param entry entry in the shopping cart
     * @return key of the created entry
     * @throws SQLException 
     */
    private int createEntry(ShoppingCartEntry entry)
            throws SQLException {
        int goodId = entry.getGoodId();
        int numberOfItems = entry.getNumberOfItems();
        ResultSet newCartRs;
        int autoId;

        PreparedStatement statement = connection.prepareStatement(
                INSERT_DEFAULT, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(GOOD_ID_COL_NUM - 1, goodId);
        statement.setInt(COUNT_OF_GOOD_COL_NUM - 1, numberOfItems);
        statement.executeUpdate();
        newCartRs = statement.getGeneratedKeys();
        newCartRs.next();
        autoId = newCartRs.getInt(1);
        return autoId;
    }

    /**
     * Creates entry in the shopping cart
     * 
     * @param entry new entry
     * @param cartId id of the cart
     * @return key of the shopping cart
     * @throws SQLException 
     */
    private int createEntry(ShoppingCartEntry entry, int cartId)
            throws SQLException {
        int goodId = entry.getGoodId();
        int numberOfItems = entry.getNumberOfItems();
        PreparedStatement statement = connection.prepareStatement(INSERT);

        statement.setInt(CART_ID_COL_NUM, cartId);
        statement.setInt(GOOD_ID_COL_NUM, goodId);
        statement.setInt(COUNT_OF_GOOD_COL_NUM, numberOfItems);
        statement.executeUpdate();

        return cartId;
    }

    @Override
    public ShoppingCart update(ShoppingCart entity)
            throws DataAccessException {
        ShoppingCart result = find(entity.getKey());
        delete(entity.getKey());
        create(entity, false);

        return result;
    }
}
