/*
 * Developed by - mGunawardhana
 * Contact email - mrgunawardhana27368@gmail.com
 * what's app - 071 - 9043372
 */

package db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DBConnection implements ServletContextListener {

    private final BasicDataSource basicDataSource = new BasicDataSource();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/company");
        basicDataSource.setPassword("1234");
        basicDataSource.setUsername("root");
        basicDataSource.setMaxTotal(2);
        basicDataSource.setInitialSize(2);

        servletContext.setAttribute("dbcp", basicDataSource);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
//    private static DBConnection dbConnection;
//    BasicDataSource bds;
//
//    private DBConnection() {
//        bds = new BasicDataSource();
//        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        bds.setUrl("jdbc:mysql://localhost:3306/company");
//        bds.setPassword("1234");
//        bds.setUsername("root");
//        bds.setMaxTotal(2);
//        bds.setInitialSize(2);
//
//    }
//
//    public static DBConnection getDbConnection() {
//        if (dbConnection == null) {
//            dbConnection = new DBConnection();
//        }
//        return dbConnection;
//    }
//
//    public Connection getConnection() throws SQLException {
//        return bds.getConnection();
//    }
}
