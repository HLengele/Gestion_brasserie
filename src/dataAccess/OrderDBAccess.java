package dataAccess;

import exception.*;
import model.Order;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {

    @Override
    public int insertOrder(Order newOrder) throws AddOrderException {
        String sql = "INSERT INTO `Order` (hour, tableNumber) VALUES (?, ?)";

        try {
            Connection connection = SingletonConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setTime(1, Time.valueOf(newOrder.getHour()));
                statement.setInt(2, newOrder.getTableNumber());
                statement.executeUpdate();

                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        return result.getInt(1);
                    }
                }
                throw new AddOrderException("Failure: no ID was generated.");
            }
        } catch (SQLException exception) {
            throw new AddOrderException("SQL error while adding the order: " + exception.getMessage());
        }
    }
//    @Override
//    public double getTotalPriceByTable(int tableNumber) throws ReadException {
//        double total = 0.0;
//
//        String query = SELECT SUM(lo.realPrice * lo.quantity) " +
//                "FROM `Order` o " +
//                "LEFT JOIN Line_Order lo ON o.orderId = lo.orderId " +
//                "WHERE o.tableNumber = ?;";
//
//        try (Connection connection = SingletonConnection.getInstance();
//             PreparedStatement ps = connection.prepareStatement(query)) {
//
//            ps.setInt(1, tableNumber);
//
//            try (ResultSet result = ps.executeQuery()) {
//                if (result.next()) {
//                    total = result.getDouble(1);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new ReadException("Error while calculating the bill: " + e.getMessage());
//        }
//
//        return total;
//    }
}