package servlet;

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
import java.sql.SQLException;

/**
 * CopyWriteOwner - mr.Gunawardhana
 * Contact - 071 - 733 1792
 *
 * © 2022 mGunawardhana,INC. ALL RIGHTS RESERVED.
 */

@WebServlet(urlPatterns = "/purchace")
public class PurchaceOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String orderId = jsonObject.getString("orderId");
        String iCode = jsonObject.getString("iCode");
        String itemName = jsonObject.getString("itemName");
        double price = Double.parseDouble(jsonObject.getString("price"));
        int Qty = Integer.parseInt(jsonObject.getString("Qty"));
        double total = Double.parseDouble(jsonObject.getString("total"));

        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = connection.prepareStatement("insert into `orders` values (?,?,?,?,?,?)");
            prepareStatement.setObject(1, orderId);
            prepareStatement.setObject(2, iCode);
            prepareStatement.setObject(3, itemName);
            prepareStatement.setObject(4, price);
            prepareStatement.setObject(5, Qty);
            prepareStatement.setObject(6, total);

            if (!(prepareStatement.executeUpdate() > 0)) {
                connection.rollback();
                connection.setAutoCommit(true);
            } else {
                JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");
                for (JsonValue orderDetail : orderDetails) {

                    String order_ID = orderDetail.asJsonObject().getString("orderId");
                    String item_Code = orderDetail.asJsonObject().getString("iCode");
                    String item_Name = orderDetail.asJsonObject().getString("itemName");
                    double Price = Double.parseDouble(orderDetail.asJsonObject().getString("price"));
                    int quantity = Integer.parseInt(orderDetail.asJsonObject().getString("Qty"));
                    double tot = Double.parseDouble(orderDetail.asJsonObject().getString("total"));

                    PreparedStatement pStatement = connection.prepareStatement("insert into `orders` values (?,?,?,?,?,?)");

                    pStatement.setObject(1,order_ID);
                    pStatement.setObject(2,item_Code);
                    pStatement.setObject(3,item_Name);
                    pStatement.setObject(4,Price);
                    pStatement.setObject(5,quantity);
                    pStatement.setObject(6,tot);

                    if (!(pStatement.executeUpdate()>0)) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        throw new RuntimeException("Order Details Issue");
                    }
                }

                connection.commit();
                connection.setAutoCommit(true);
                JsonObjectBuilder rjo = Json.createObjectBuilder();
                rjo.add("state", "Success");
                rjo.add("message", "Order Success !");
                rjo.add("data", "");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(rjo.build());
            }

        } catch (SQLException | RuntimeException e) {
            JsonObjectBuilder rjo = Json.createObjectBuilder();
            rjo.add("state", "Error");
            rjo.add("message", e.getLocalizedMessage());
            rjo.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(rjo.build());
        }


    }
}
