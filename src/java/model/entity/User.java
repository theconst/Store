/*
 * User.java 10.01.2016
 */
package model.entity;

/**
 * User of the store
 *
 * @author Kostiantyn Kovalchuk
 */
public class User implements Entity<Integer> {

    /**
     * Id of the user
     */
    private int id;

    /**
     * Login of the user
     */
    private String login;

    /**
     * Password of the user
     */
    private String password;

    /**
     * User's permission
     */
    private String permission;

    /**
     * Get user's id
     *
     * @return id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Assign new value to the user's id
     *
     * @param id new user's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get user's login
     *
     * @return user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Assign new value to the user's login
     *
     * @param login user's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get user's password
     *
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Assign new value to the user's password
     *
     * @param password user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return user's permission in string format
     */
    public String getPermission() {
        return permission;
    }

    /**
     * New user's permission
     *
     * @param permission user's permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public Integer getKey() {
        return id;
    }

}
