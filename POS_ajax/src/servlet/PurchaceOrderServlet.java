package servlet;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CopyWriteOwner - mr.Gunawardhana
 * Contact - 071 - 733 1792
 * <p>
 * © 2022 mGunawardhana,INC. ALL RIGHTS RESERVED.
 */

@WebServlet(urlPatterns = "/purchace")
public class PurchaceOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("i m here 01");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String order_id = jsonObject.getString("order_id");
        String order_date = jsonObject.getString("order_date");
        String customer_id = jsonObject.getString("customer_id");
        String customer_name = jsonObject.getString("customer_name");
        String customer_contact = jsonObject.getString("customer_contact");


        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {

            System.out.println("i m here 02");

            connection.setAutoCommit(false);

            PreparedStatement pstm = connection.prepareStatement("insert into `orders` values (?,?,?,?,?)");
            pstm.setObject(1, order_id);
            pstm.setObject(2, order_date);
            pstm.setObject(3, customer_id);
            pstm.setObject(4, customer_name);
            pstm.setObject(5, customer_contact);

            if (!(pstm.executeUpdate() > 0)) {

                System.out.println("i m here 03");


                connection.rollback();
                connection.setAutoCommit(true);
                throw new RuntimeException("Order Issue");
            } else {

                System.out.println("i m here 04");

                JsonArray orderDetails = jsonObject.getJsonArray("fullObj");
                for (JsonValue orderDetail : orderDetails) {
                    System.out.println("i m here 06");


                    String item_code = orderDetail.asJsonObject().getString("item_code");
                    String item_name = orderDetail.asJsonObject().getString("item_name");
                    double item_price = Double.parseDouble(orderDetail.asJsonObject().getString("item_price"));
                    int item_quantity = Integer.parseInt(orderDetail.asJsonObject().getString("item_quantity"));
                    double item_total = Double.parseDouble(orderDetail.asJsonObject().getString("item_total"));

                    PreparedStatement pstms = connection.prepareStatement("insert into `OrderDetails` values(?,?,?,?,?,?)");

                    pstms.setObject(1, order_id);
                    pstms.setObject(2, item_code);
                    pstms.setObject(3, item_name);
                    pstms.setObject(4, item_price);
                    pstms.setObject(5, item_quantity);
                    pstms.setObject(6, item_total);

                    if (!(pstms.executeUpdate() > 0)) {
                        System.out.println("i m here 07");

                        connection.rollback();
                        connection.setAutoCommit(true);
                        throw new RuntimeException("Order Details Issue");
                    }
                }

                connection.commit();
                connection.setAutoCommit(true);
                System.out.println("i m here 08");

                JsonObjectBuilder error = Json.createObjectBuilder();
                error.add("state", "Success");
                error.add("message", "Order Successfully Purchased..!");
                error.add("data", "");
                resp.getWriter().print(error.build());
            }

        } catch (SQLException | RuntimeException e) {
            System.out.println("i m here 09");

            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getLocalizedMessage());//TODO check here ............
            error.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(error.build());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");

        switch (option) {

            case "Order":

                try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {

                    PreparedStatement pstm = connection.prepareStatement("select * from `orders`");
                    ResultSet resultSet = pstm.executeQuery();
                    JsonArrayBuilder allOrders = Json.createArrayBuilder();

                    while (resultSet.next()) {
                        JsonObjectBuilder Order = Json.createObjectBuilder();
                        Order.add("order_id", resultSet.getString("order_id"));
                        Order.add("order_date", resultSet.getString("order_date"));
                        Order.add("customer_id", resultSet.getString("customer_id"));
                        Order.add("customer_name", resultSet.getString("customer_name"));
                        Order.add("customer_contact", resultSet.getString("customer_contact"));
                        allOrders.add(Order.build());
                    }

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("state", "OK");
                    objectBuilder.add("message", "Successfully loaded ..!");
                    objectBuilder.add("data", allOrders.build());
                    resp.getWriter().print(objectBuilder.build());

                } catch (SQLException e) {
                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Error");
                    rjo.add("message", e.getLocalizedMessage());
                    rjo.add("data", "");
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().print(rjo.build());
                }

                break;

            case "OrderDetails":

                try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {


                    PreparedStatement pstm2 = connection.prepareStatement("select * from `orderDetails`");
                    ResultSet resultSet2 = pstm2.executeQuery();
                    JsonArrayBuilder allOrdersDetails = Json.createArrayBuilder();

                    System.out.println("I'm in second try catch now !");

                    while (resultSet2.next()) {

                        JsonObjectBuilder OrderDetails = Json.createObjectBuilder();
                        OrderDetails.add("order_id", resultSet2.getString("order_id"));
                        OrderDetails.add("item_code", resultSet2.getString("item_code"));
                        OrderDetails.add("item_name", resultSet2.getString("item_name"));
                        OrderDetails.add("item_price", resultSet2.getDouble("item_price"));
                        OrderDetails.add("item_quantity", resultSet2.getInt("item_quantity"));
                        OrderDetails.add("item_total", resultSet2.getDouble("item_total"));

                        allOrdersDetails.add(OrderDetails.build());

                        System.out.println(OrderDetails.build().toString());
                    }

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("state", "OK");
                    objectBuilder.add("message", "Successfully loaded ..!");
                    objectBuilder.add("data", allOrdersDetails.build());
                    resp.getWriter().print(objectBuilder.build());


                } catch (SQLException e) {
                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Error");
                    rjo.add("message", e.getLocalizedMessage());
                    rjo.add("data", "");
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().print(rjo.build());
                }
                break;
        }


    }


}
