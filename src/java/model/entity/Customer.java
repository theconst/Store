/*
 * Customer.java 4.01.2016
 */
package model.entity;

/**
 * Customer
 *
 * @author Kostiantyn Kovalchuk
 */
public class Customer extends User {

    /**
     * Customer's name
     */
    private String name;

    /**
     * Customer's address
     */
    private String address;

    /**
     * Is true if customer is in blakclis
     */
    private boolean inBlackList;

    /**
     * Customer's user account
     */
    private User wrapped;

    /**
     * Constructs new customer corresponding to the user account
     *
     * @param user user account
     */
    public Customer(User user) {
        this.wrapped = user;
    }

    @Override
    public int getId() {
        return wrapped.getId();
    }

    @Override
    public void setId(int id) {
        wrapped.setId(id);
    }

    /**
     * @return name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Assigns a new name to the customer
     *
     * @param name new name of the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Assigns new address to the customer
     *
     * @param address new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return true if the customer is in blacklist
     */
    public boolean isInBlackList() {
        return inBlackList;
    }

    /**
     * Add/remove customer from the blacklist
     *
     * @param inBlackList is true if the customer is in blacklist, false
     * otherwise
     */
    public void setInBlackList(boolean inBlackList) {
        this.inBlackList = inBlackList;
    }

    @Override
    public Integer getKey() {
        return getId();
    }

    @Override
    public String getLogin() {
        return wrapped.getLogin();
    }

    @Override
    public void setLogin(String login) {
        wrapped.setLogin(login);
    }

    @Override
    public String getPassword() {
        return wrapped.getPassword();
    }

    @Override
    public void setPassword(String password) {
        wrapped.setPassword(password);
    }

    @Override
    public String getPermission() {
        return wrapped.getPermission();
    }

    @Override
    public void setPermission(String permission) {
        wrapped.setPermission(permission);
    }
}
