/*
 * ShoppingCart.java 4.01.2016
 */
package model.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Shopping cart - user's request
 *
 * @author Kostiantyn Kovalchuk
 */
public class ShoppingCart implements Entity<Integer> {

    /**
     * Entry of the shopping cart
     */
    public static class ShoppingCartEntry {

        /**
         * Id of the good
         */
        private int goodId;

        /**
         * Number of items in the cart
         */
        private int numberOfItems;

        /**
         * @return id of the good in the entry
         */
        public int getGoodId() {
            return goodId;
        }

        /**
         * Set the id of the good in the entry
         *
         * @param goodId new good id
         */
        public void setGoodId(int goodId) {
            this.goodId = goodId;
        }

        /**
         * Get number of items in the cart
         *
         * @return number of items
         */
        public int getNumberOfItems() {
            return numberOfItems;
        }

        /**
         * Set new value to the number of items
         *
         * @param numberOfItems new number of items
         */
        public void setNumberOfItems(int numberOfItems) {
            this.numberOfItems = numberOfItems;
        }
    }

    /**
     * Default number of items added to the cart
     */
    private static final int DEFAULT_GOOD_COUNT = 1;

    /**
     * Id of the shopping cart
     */
    private int id;

    /**
     * Items in the shopping cart
     */
    private List<ShoppingCartEntry> items = new ArrayList<>();

    @Override
    public Integer getKey() {
        return id;
    }

    /**
     * Get the id of the shopping cart
     *
     * @return shopping cart's id
     */
    public int getId() {
        return id;
    }

    /**
     * Assign new value to the id
     *
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get list of the goods in the shopping cart
     *
     * @return list of goods in the shopping cart
     */
    public List<ShoppingCartEntry> getGoods() {
        return items;
    }

    /**
     * Set the list of shopping cart goods
     *
     * @param goods goods in the shopping cart
     */
    public void setGoods(List<ShoppingCartEntry> goods) {
        this.items = goods;
    }

    /**
     * Adds good to the cart
     *
     * @param goodId id of the goo
     * @param numberOfItems number of items in the cart
     */
    public void addGood(int goodId, int numberOfItems) {
        ShoppingCartEntry entry = new ShoppingCartEntry();

        for (ShoppingCartEntry el : items) {
            if (el.goodId == goodId) {
                el.numberOfItems += numberOfItems;
                return;
            }
        }
        entry.goodId = goodId;
        entry.numberOfItems = numberOfItems;
        items.add(entry);
    }

    /**
     * Adds good to the cart
     *
     * @param goodId id of the good
     */
    public void addGood(int goodId) {
        addGood(goodId, DEFAULT_GOOD_COUNT);
    }

    /**
     * Adds good to the cart
     *
     * @param good good
     */
    public void addGood(Good good) {
        addGood(good.getId());
    }

    /**
     * Removes good from the cart
     *
     * @param goodId id of the good
     * @param numberOfItems number of items in the cart
     */
    public void removeGood(int goodId, int numberOfItems) {
        Iterator<ShoppingCartEntry> it = items.iterator();

        while (it.hasNext()) {
            ShoppingCartEntry entry = it.next();
            if (entry.goodId == goodId) {
                entry.numberOfItems -= numberOfItems;
                if (entry.numberOfItems <= 0) {
                    it.remove();
                }
                return;
            }
        }
    }

    /**
     * Removes good from the cart
     *
     * @param good good
     */
    public void removeGood(Good good) {
        removeGood(good.getId(), DEFAULT_GOOD_COUNT);
    }

    /**
     * Checks if the cart is empty
     *
     * @return true if there are no items in the cart
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
