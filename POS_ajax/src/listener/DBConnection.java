/*
 * Developed by - mGunawardhana
 * Contact email - mrgunawardhana27368@gmail.com
 * what's app - 071 - 9043372
 */

package listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class DBConnection implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        BasicDataSource basicDataSource = new BasicDataSource();

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
        try {
            ((BasicDataSource) servletContextEvent.getServletContext().getAttribute("dbcp")).close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
