/*
 * Developed by - mGunawardhana
 * Contact email - mrgunawardhana27368@gmail.com
 * what's app - 071 - 9043372
 */
package servlet;

import lombok.SneakyThrows;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    /**
     * get all items option successfully implemented using doGet
     */

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from item");
        ResultSet rst = preparedStatement.executeQuery();

        JsonArrayBuilder allItems = Json.createArrayBuilder();

        while (rst.next()) {

            JsonObjectBuilder item = Json.createObjectBuilder();

            item.add("itemId", rst.getString("itemId"));
            item.add("itemName", rst.getString("itemName"));
            item.add("qty", rst.getString("qty"));
            item.add("unitPrice", rst.getDouble("unitPrice"));
            allItems.add(item.build());

        }
        resp.addHeader("Content-Type", "application/json");
        resp.getWriter().print(allItems.build());
    }

    /* update item option */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JsonReader reader = Json.createReader(req.getReader());
            JsonObject item = reader.readObject();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET itemName= ? , qty=? , unitPrice=? WHERE itemId=?");

            pstm.setObject(4, item.getString("itemId"));
            pstm.setObject(1, item.getString("itemName"));
            pstm.setObject(2, item.getString("qty"));
            pstm.setObject(3, item.getString("unitPrice"));

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state", "OK");
                responseObject.add("message", "Successfully Updated !");
                responseObject.add("data", "");
                resp.getWriter().print(responseObject.build());
            } else {
                throw new RuntimeException("Illegal id caught please check and re enter !");
            }

        } catch (ClassNotFoundException e) {

            JsonObjectBuilder error_for_item_update = Json.createObjectBuilder();
            error_for_item_update.add("state", "Error");
            error_for_item_update.add("message", e.getLocalizedMessage());
            error_for_item_update.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(error_for_item_update.build());

        } catch (SQLException e) {

            JsonObjectBuilder error_for_item_update = Json.createObjectBuilder();
            error_for_item_update.add("state", "Error");
            error_for_item_update.add("message", e.getLocalizedMessage());
            error_for_item_update.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(error_for_item_update.build());
        }
    }

    /**
     * add item option successfully implemented using doPost
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement p_statement = connection.prepareStatement("insert into item values(?,?,?,?)");

            p_statement.setObject(1, req.getParameter("itemId"));
            p_statement.setObject(2, req.getParameter("itemName"));
            p_statement.setObject(3, req.getParameter("qty"));
            p_statement.setObject(4, req.getParameter("unitPrice"));

            if (p_statement.executeUpdate() > 0) {

                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state", "OK");
                responseObject.add("message", "Successfully Added !");
                responseObject.add("data", "");
                resp.getWriter().print(responseObject.build());

            }

        } catch (ClassNotFoundException e) {

            JsonObjectBuilder error_for_item = Json.createObjectBuilder();
            error_for_item.add("state", "Error");
            error_for_item.add("message", e.getLocalizedMessage());
            error_for_item.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(error_for_item.build());

        } catch (SQLException e) {

            JsonObjectBuilder error_for_item = Json.createObjectBuilder();
            error_for_item.add("state", "Error");
            error_for_item.add("message", e.getLocalizedMessage());
            error_for_item.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(error_for_item.build());

        }
    }

    /**
     * delete item option successfully implemented using doDelete
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE itemId=?");
            pstm.setObject(1, req.getParameter("itemId"));
            System.out.println(req.getParameter("itemId"));

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder repObj = Json.createObjectBuilder();
                repObj.add("state", "OK");
                repObj.add("message", "Successfully Deleted !");
                repObj.add("data", "");
                resp.getWriter().print(repObj.build());
            } else {
                throw new RuntimeException("Thee is no such item for that ID ");
            }

        } catch (RuntimeException e) {

            JsonObjectBuilder repObj = Json.createObjectBuilder();
            repObj.add("state", "Error");
            repObj.add("message", e.getLocalizedMessage());
            repObj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(repObj.build());

        } catch (ClassNotFoundException | SQLException e) {

            JsonObjectBuilder repObj = Json.createObjectBuilder();
            repObj.add("state", "Error");
            repObj.add("message", e.getLocalizedMessage());
            repObj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(repObj.build());

        }
    }
}


