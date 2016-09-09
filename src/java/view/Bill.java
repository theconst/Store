/*
 * Bill.java 19.01.2016
 */
package view;

import dao.DataAccessException;
import java.util.ArrayList;
import java.util.List;
import model.action.DataAccessFacade;
import model.entity.Good;
import model.entity.ShoppingCart;
import model.entity.ShoppingCart.ShoppingCartEntry;

/**
 * Class that represents the shopping bill;
 *
 * @author Kostiantyn Kovalchuk
 */
public class Bill {

    /**
     * Entry of the bill
     */
    public static class BillEntry {

        /**
         * Id of the good
         */
        private int goodId;

        /**
         * Name of the good
         */
        private String goodName;

        /**
         * Number of items
         */
        private int goodCount;

        /**
         * Price of the good
         */
        private int goodPrice;

        /**
         * @return price of the good
         */
        public int getGoodId() {
            return goodId;
        }

        /**
         * @return name of the good
         */
        public String getGoodName() {
            return goodName;
        }

        /**
         * @return number of items bought
         */
        public int getGoodCount() {
            return goodCount;
        }

        /**
         * @return price of the good
         */
        public int getGoodPrice() {
            return goodPrice;
        }
    }

    /**
     * List of etries in the bill
     */
    private List<BillEntry> items = new ArrayList<>();

    /**
     * Total price of the bill
     */
    private int totalPrice = 0;

    /**
     * Creates new bill that corresponds to the shopping cart
     *
     * @param sc
     * @throws DataAccessException
     */
    private Bill(ShoppingCart sc) throws DataAccessException {

        for (ShoppingCartEntry cartEntry : sc.getGoods()) {
            int goodId = cartEntry.getGoodId();
            Good good = DataAccessFacade.getGood(goodId);

            BillEntry billEntry = new BillEntry();
            billEntry.goodId = good.getId();
            billEntry.goodName = good.getName();
            billEntry.goodCount = cartEntry.getNumberOfItems();
            billEntry.goodPrice = good.getPrice();
            totalPrice += billEntry.goodCount * billEntry.goodPrice;
            items.add(billEntry);
        }
    }

    /**
     * Factory method that creates bill
     *
     * @param sc shopping cart
     * @return
     * @throws DataAccessException
     */
    public static Bill receiveBill(ShoppingCart sc) throws DataAccessException {
        return new Bill(sc);
    }

    /**
     * Get items of the bill
     *
     * @return
     */
    public List<BillEntry> getItems() {
        return items;
    }

    /**
     * Get total price of the bill
     *
     * @return
     */
    public int getTotalPrice() {
        return totalPrice;
    }
}
