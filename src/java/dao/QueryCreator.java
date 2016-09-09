/*
 * QueryCreator.java 4.01.2016
 */
package dao;


/**
 * Auxiliary class for creation typical queries
 *
 * @author Kostiantyn Kovalchuk
 */
public class QueryCreator {

    private static final String SELECT = " SELECT ";
    private static final String ALL = " * ";
    private static final String COMMA = " , ";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = " = ";
    private static final String END = " ; ";
    private static final String FROM = " FROM ";
    private static final String DOT = ".";
    private static final String UNKNOWN = " ? ";
    private static final String JOIN = " JOIN ";
    private static final String INSERT = " INSERT IGNORE INTO ";
    private static final String VALUES_OPEN = " VALUES(";
    private static final String VALUES_CLOSE = ") ";
    private static final String UPDATE = " UPDATE ";
    private static final String SET = " SET ";
    private static final String DELETE = " DELETE ";
    private static final String IGNORE = " IGNORE ";

    
    /**
     * Creates select all statement for the table from database
     *
     * @param dbName database
     * @param tbName table
     * @return string with query
     */
    public static String createSelectAll(String dbName, String tbName) {
        return (SELECT + ALL + FROM + dbName + DOT + tbName + END).trim();
    }

    /**
     * Creates select all statements for some columns
     *
     * @param what specified columns
     * @param dbName name of the database
     * @param tbName name of the table
     * @return String with the query
     */
    public static String createSelectAll(String what, String dbName,
            String tbName) {
        return (SELECT + what + FROM + dbName + DOT + tbName + END).trim();

    }

    /**
     * Creates select by key query
     *
     * @param dbName name of the database
     * @param tbName name of the table
     * @param keyName name of the key
     * @return
     */
    public static String createSelectByKey(String dbName,
            String tbName, String keyName) {
        return (SELECT + ALL + FROM + dbName + DOT + tbName + WHERE
                + tbName + DOT + keyName + EQUALS + UNKNOWN + END).trim();
    }

    /**
     * Create the insert statement for the whole table
     *
     * @param dbName name of the database
     * @param tbName name of the table
     * @param numberOfValues number of values in the table
     * @return insert statement
     */
    public static String createInsert(String dbName, String tbName,
            int numberOfValues) {
        StringBuilder sb = new StringBuilder(INSERT).append(dbName).append(DOT)
                .append(tbName).append(VALUES_OPEN);

        for (int i = 0; i < numberOfValues - 1; ++i) {
            sb.append(UNKNOWN + COMMA);
        }
        sb.append(UNKNOWN).append(VALUES_CLOSE);
        return sb.toString();
    }

    /**
     * Creates delete statement for the table
     *
     * @param dbName
     * @param tbName
     * @param keyName
     * @return
     */
    public static String createDelete(String dbName,
            String tbName, String keyName) {
        return (DELETE + IGNORE + FROM + dbName + DOT + tbName + WHERE
                + tbName + DOT + keyName + EQUALS + UNKNOWN + END).trim();
    }
    
    /**
     * Creates custom select query
     * 
     * @param what query string
     * @param fromWhere source
     * @param condition where part of the statement
     * @return 
     */
    public static String createCustomSelect(String what, String fromWhere,
            String condition) {
        return (SELECT + what + FROM + fromWhere + WHERE + condition 
                + END).trim();
    }
}
