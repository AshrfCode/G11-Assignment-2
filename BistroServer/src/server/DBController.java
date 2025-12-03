package server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import common.Order;

public class DBController {

    private static final String URL =
            "jdbc:mysql://localhost:3306/Bistro?serverTimezone=Asia/Jerusalem&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Aa123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // --------------------------------------------------------------------
    //  GET ALL ORDERS
    // --------------------------------------------------------------------
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `Order`";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_number"),
                        rs.getDate("order_date"),
                        rs.getInt("number_of_guests"),
                        rs.getInt("confirmation_code"),
                        rs.getInt("subscriber_id"),
                        rs.getDate("date_of_placing_order")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // --------------------------------------------------------------------
    //  UPDATE ORDER DATE
    // --------------------------------------------------------------------
    public boolean updateOrderDate(int orderNumber, Date newDate) {
        String sql = "UPDATE `Order` SET order_date = ? WHERE order_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, newDate);
            stmt.setInt(2, orderNumber);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --------------------------------------------------------------------
    //  UPDATE NUMBER OF GUESTS (matches your table)
    // --------------------------------------------------------------------
    public boolean updateNumberOfGuests(int orderNumber, int guests) {
        String sql = "UPDATE `Order` SET number_of_guests = ? WHERE order_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, guests);
            stmt.setInt(2, orderNumber);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
