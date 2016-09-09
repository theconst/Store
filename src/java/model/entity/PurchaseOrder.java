/*
 * PurchaseOrder.java 4.01.2016
 */
package model.entity;

/**
 * Purchase order - customer's order
 *
 * @author Kostiantyn Kovalchuk
 */
public class PurchaseOrder implements Entity<Integer> {

    /**
     * Id of the customer
     */
    private int custromerId;

    /**
     * Id of the shopping cart
     */
    private int shoppingCartId;

    /**
     * Number of the order
     */
    private int number;

    /**
     * @return number of the order
     */
    public int getNumber() {
        return number;
    }

    /**
     * Assign new number to the order
     *
     * @param number new number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return new customers id
     */
    public int getCustromerId() {
        return custromerId;
    }

    /**
     * Assign new id to the customer
     * 
     * @param custromerId new customer's id
     */
    public void setCustromerId(int custromerId) {
        this.custromerId = custromerId;
    }

    /**
     * @return shopping cart id corresponding to the order
     */
    public int getShoppingCartId() {
        return shoppingCartId;
    }

    /**
     * Assign new value to the shopping cart id
     * @param shoppingCartId new shopping cart id
     */
    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    public Integer getKey() {
        return number;
    }
}
