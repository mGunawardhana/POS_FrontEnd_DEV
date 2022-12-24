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

   BasicDataSource basicDataSource = new BasicDataSource();

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

}
