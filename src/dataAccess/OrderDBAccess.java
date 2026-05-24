package dataAccess;

import exception.*;
import model.Order;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {


    @Override
    public ArrayList<Order> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<Order> orders = new ArrayList<>();

            String sql = "SELECT * FROM `Order` ORDER BY orderId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while(data.next()) {
                Time sqlTime = data.getTime("hour");
                LocalTime localTime = (sqlTime != null) ? sqlTime.toLocalTime() : LocalTime.now();
                Order order = new Order(
                        data.getInt("orderId"),
                        localTime,
                        data.getInt("tableNumber")
                );
                orders.add(order);
            }
            return orders;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }

    @Override
    public Order readById(int id) throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            Order order = null;

            String sql = "SELECT * FROM `Order` WHERE orderId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet data = statement.executeQuery();

            if (data.next()) {
                Time sqlTime = data.getTime("hour");
                LocalTime localTime = (sqlTime != null) ? sqlTime.toLocalTime() : LocalTime.now();
                order = new Order(
                        data.getInt("orderId"),
                        localTime,
                        data.getInt("tableNumber")
                );
            }
            return order;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }


    @Override
    public double getTotalPriceByTable(int tableNumber) throws ReadException {
        double total = 0.0;
        String query = "SELECT SUM(lo.realPrice * lo.quantity) AS total_addition " +
                "FROM Line_Order lo " +
                "JOIN `Order` o ON lo.orderId = o.orderId " +
                "WHERE o.tableNumber = ?;";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, tableNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total_addition");
                }
            }

        } catch (SQLException e) {
            throw new ReadException("Erreur lors du calcul de l'addition : " + e.getMessage());
        }

        return total;
    }
}