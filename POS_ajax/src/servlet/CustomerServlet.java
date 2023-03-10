package servlet;

/**
 * CopyWriteOwner - mr.Gunawardhana
 * Contact - 071 - 733 1792
 * <p>
 * © 2022 mGunawardhana,INC. ALL RIGHTS RESERVED.
 */

import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    /**
     * get all customer details option successfully implemented using doGet method
     */
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();

            while (rst.next()) {
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id", rst.getString("id"));
                customer.add("name", rst.getString("name"));
                customer.add("address", rst.getString("address"));
                customer.add("contact", rst.getString("contact"));
                allCustomers.add(customer.build());

            }

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state", "OK");
            objectBuilder.add("message", "Successfully loaded ..!");
            objectBuilder.add("data", allCustomers.build());
            resp.getWriter().print(objectBuilder.build());

        } catch (SQLException e) {
            JsonObjectBuilder rjo = Json.createObjectBuilder();
            rjo.add("state", "Error");
            rjo.add("message", e.getLocalizedMessage());
            rjo.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(rjo.build());
        }

    }

    /**
     * save customer option successfully implemented using doPost method
     */
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?,?)");

            pstm.setObject(1, req.getParameter("id"));
            pstm.setObject(2, req.getParameter("name"));
            pstm.setObject(3, req.getParameter("address"));
            pstm.setObject(4, req.getParameter("contact"));

            boolean b = pstm.executeUpdate() > 0;

            if (b) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state", "OK");
                responseObject.add("message", "Successfully Added !");
                responseObject.add("data", "");
                resp.getWriter().print(responseObject.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getLocalizedMessage());
            error.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(error.build());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customer = reader.readObject();

        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {

            PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name= ? , address=? , contact=? WHERE id=?");

            pstm.setObject(4, customer.getString("id"));
            pstm.setObject(1, customer.getString("name"));
            pstm.setObject(2, customer.getString("address"));
            pstm.setObject(3, customer.getString("contact"));

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state", "Ok");
                responseObject.add("message", "Successfully Updated..!");
                responseObject.add("data", "");
                resp.getWriter().print(responseObject.build());

            } else {
                throw new RuntimeException("Wrong ID, Please Check The ID..!");
            }


        } catch (RuntimeException e) {
            JsonObjectBuilder rjo = Json.createObjectBuilder();
            rjo.add("state", "Error");
            rjo.add("message", e.getLocalizedMessage());
            rjo.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(rjo.build());

        } catch (SQLException e) {
            JsonObjectBuilder rjo = Json.createObjectBuilder();
            rjo.add("state", "Error");
            rjo.add("message", e.getLocalizedMessage());
            rjo.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(rjo.build());
        }
    }

    /**
     * delete customer option successfully implemented using doDelete method
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("delete from customer where id=?");

            pstm.setObject(1, req.getParameter("id"));

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder rjo = Json.createObjectBuilder();
                rjo.add("state", "OK");
                rjo.add("message", "Successfully Deleted ..!");
                rjo.add("data", "");
                resp.getWriter().print(rjo.build());

            } else {
                throw new RuntimeException("There is no such customer for that ID");
            }

        } catch (RuntimeException e) {
            JsonObjectBuilder rjo = Json.createObjectBuilder();
            rjo.add("state", "Error");
            rjo.add("message", e.getLocalizedMessage());
            rjo.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(rjo.build());

        } catch (SQLException e) {
            JsonObjectBuilder rjo = Json.createObjectBuilder();
            rjo.add("state", "Error");
            rjo.add("message", e.getLocalizedMessage());
            rjo.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(rjo.build());
        }
    }
}

