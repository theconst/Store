/*
 * PurchaseOrderDAO.java 18.01.2016
 */
package dao;

import static dao.DatabaseConstants.PURCHASE_ORDER_TBL;
import model.entity.PurchaseOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static dao.DatabaseConstants.DATABASE_NAME;
import java.sql.Statement;

/**
 * DAO class for the purchase order
 *
 * @author Kostiantyn Kovalchuk
 */
public class PurchaseOrderDAO extends AbstractOneTableDAO<Integer, PurchaseOrder> {

    /**
     * Number column of the table
     */
    private static final String ORDER_NUM_COL = "number";

    /**
     * Id of the cart
     */
    private static final String CART_ID_COL = "shopping_cart_id";

    /**
     * Id of the customer
     */
    private static final String CUSTOMER_ID_COL = "customer_id";

    /**
     * Count of columns in the table
     */
    private static final int COL_COUNT = 3;

    /**
     * id of the customer column
     */
    private static final int CUSTOMER_ID_COL_NUM = 1;

    /**
     * id of the cart column
     */
    private static final int CART_ID_COL_NUM = 2;

    /**
     * id of the order column
     */
    private static final int ORDER_NUM_COL_NUM = 3;

    /**
     * Inserts the entry with default key
     */
    private static final String INSERT_DEFAULT = "INSERT INTO " + DATABASE_NAME
            + "." + PURCHASE_ORDER_TBL + "(" + CART_ID_COL + ", "
            + CUSTOMER_ID_COL + ") " + "VALUES(?, ?);";

    
    /**
     * Selects entries by key
     */
    private static final String SELECT_BY_KEY
            = QueryCreator.createSelectByKey(DATABASE_NAME,
                    PURCHASE_ORDER_TBL, ORDER_NUM_COL);

    /**
     * Selects all the entries in table
     */
    private static final String SELECT_ALL
            = QueryCreator.createSelectAll(DATABASE_NAME, PURCHASE_ORDER_TBL);

    /**
     * Inserts entry into the table
     */
    private static final String INSERT
            = QueryCreator.createInsert(DATABASE_NAME,
                    PURCHASE_ORDER_TBL, COL_COUNT);

    /**
     * Deletes entities from the table
     */
    private static final String DELETE
            = QueryCreator.createDelete(DATABASE_NAME, PURCHASE_ORDER_TBL,
                    ORDER_NUM_COL);

    public PurchaseOrderDAO(Connection connection) throws DataAccessException {
        super(connection);
    }

    @Override
    protected PurchaseOrder buildEntity(ResultSet rs) throws SQLException {
        PurchaseOrder order = new PurchaseOrder();
        int orderNum = rs.getInt(ORDER_NUM_COL);
        int cartId = rs.getInt(CART_ID_COL);
        int customerId = rs.getInt(CUSTOMER_ID_COL);

        order.setCustromerId(customerId);
        order.setNumber(orderNum);
        order.setShoppingCartId(cartId);
        return order;
    }

    @Override
    protected PreparedStatement prepareFindByKeyQuery(Integer key) throws SQLException {
        PreparedStatement findByKey = connection.prepareStatement(SELECT_BY_KEY);

        findByKey.setInt(CUSTOMER_ID_COL_NUM, key);
        return findByKey;
    }

    @Override
    protected PreparedStatement prepareFindAllQuery() throws SQLException {
        return connection.prepareStatement(SELECT_ALL);
    }

    @Override
    protected PreparedStatement prepareInsertQuery(PurchaseOrder entity) throws SQLException {
        PreparedStatement insert = connection.prepareStatement(INSERT);

        insert.setInt(CUSTOMER_ID_COL_NUM, entity.getCustromerId());
        insert.setInt(ORDER_NUM_COL_NUM, entity.getNumber());
        insert.setInt(CART_ID_COL_NUM, entity.getShoppingCartId());
        return insert;
    }

    /**
     * Creates the default order by the customer id
     *
     * @param order
     * @param setDefaultKey
     * @return order id
     * @throws dao.DataAccessException
     */
    @Override
    public Integer create(PurchaseOrder order, boolean setDefaultKey) throws DataAccessException {

        if (!setDefaultKey) {
            return create(order);
        }

        try {
            PreparedStatement createDefaultOrder
                    = connection.prepareStatement(INSERT_DEFAULT, Statement.RETURN_GENERATED_KEYS);
            int shoppingCartId = order.getShoppingCartId();
            int customerId = order.getCustromerId();
            ResultSet rs;

            createDefaultOrder.setInt(CART_ID_COL_NUM - 1, shoppingCartId);
            createDefaultOrder.setInt(ORDER_NUM_COL_NUM - 1, customerId);
            createDefaultOrder.executeUpdate();
            rs = createDefaultOrder.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    protected PreparedStatement prepareDeleteQuery(Integer key) throws SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE);

        deleteStatement.setInt(1, key);
        return deleteStatement;
    }
}
