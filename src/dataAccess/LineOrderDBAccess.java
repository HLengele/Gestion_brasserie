package dataAccess;

import exception.ReadException;
import model.LineOrder;
import java.sql.*;
import java.util.ArrayList;

public class LineOrderDBAccess implements LineOrderDataAccess {

    @Override
    public void insertLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
        String sql = "INSERT INTO Line_Order (quantity, realPrice, orderId, beerId) VALUES (?, ?, ?, ?)";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setDouble(2, realPrice);
            ps.setInt(3, orderId);
            ps.setInt(4, beerId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error while inserting the order line: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<LineOrder> getLineOrdersByTable(int tableNumber) throws ReadException {
        ArrayList<LineOrder> lineOrders = new ArrayList<>();

        String query = "SELECT lo.beerId, b.name, lo.quantity, lo.realPrice " +
                "FROM `Order` o " +
                "LEFT JOIN Line_Order lo ON o.orderId = lo.orderId " +
                "LEFT JOIN Beer b ON lo.beerId = b.beerId " +
                "WHERE o.tableNumber = ? AND lo.orderId IS NOT NULL;";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, tableNumber);

            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    LineOrder line = new LineOrder(
                            result.getInt("beerId"),
                            result.getString("name"),
                            result.getInt("quantity"),
                            result.getDouble("realPrice")
                    );
                    lineOrders.add(line);
                }
            }

        } catch (SQLException e) {
            throw new ReadException("Error while fetching line orders for table: " + e.getMessage());
        }

        return lineOrders;
    }
}