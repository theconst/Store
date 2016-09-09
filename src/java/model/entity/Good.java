/*
 * Good.java 4.01.2016
 */
package model.entity;

/**
 * Good - a commodity item
 *
 * @author Kostiantyn Kovalchuk
 */
public class Good implements Entity<Integer> {

    /**
     * Id of the item
     */
    private int id;

    /**
     * Name of the item
     */
    private String name;

    /**
     * Price of the item
     */
    private int price;

    /**
     * Description of the item
     */
    private String description;

    /**
     * Type of the good
     */
    private String type;

    /**
     * @return id of the item
     */
    public int getId() {
        return id;
    }

    /**
     * Assign new id to the item
     *
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Assign new name to the item
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return item's price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Assign new price to the item
     *
     * @param price new price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Get item's description
     *
     * @return description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set item's description
     *
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get type of the item
     *
     * @return type of the item
     */
    public String getType() {
        return type;
    }

    /**
     * Assign new type to the good
     *
     * @param type new type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getKey() {
        return id;
    }
}
