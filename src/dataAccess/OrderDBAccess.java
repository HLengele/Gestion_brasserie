package dataAccess;

import exception.*;
import model.Order;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {

    @Override
    public void insertOrder(Order newOrder) throws AddOrderException {
        try {
            Connection connection = SingletonConnection.getInstance();

            // Ajout des colonnes orderDate et status selon les schémas
            String sql = "INSERT INTO `Order` (orderDate, hour, status, tableNumber, employeeId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Conversion de LocalDate et LocalTime vers les types supportés par JDBC
            statement.setDate(1, Date.valueOf(newOrder.getOrderDate()));
            statement.setTime(2, Time.valueOf(newOrder.getHour()));
            statement.setString(3, newOrder.getStatus());
            statement.setInt(4, newOrder.getTableNumber());
            statement.setInt(5, newOrder.getEmployeeId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new AddOrderException(exception.getMessage());
        }
    }

    @Override
    public void updateOrder(Order orderToUpdate) throws UpdateOrderException {
        try {
            Connection connection = SingletonConnection.getInstance();

            String sql = "UPDATE `Order` SET orderDate = ?, hour = ?, status = ?, tableNumber = ?, employeeId = ? WHERE orderId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, Date.valueOf(orderToUpdate.getOrderDate()));
            statement.setTime(2, Time.valueOf(orderToUpdate.getHour()));
            statement.setString(3, orderToUpdate.getStatus());
            statement.setInt(4, orderToUpdate.getTableNumber());
            statement.setInt(5, orderToUpdate.getEmployeeId());
            statement.setInt(6, orderToUpdate.getOrderId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new UpdateOrderException(exception.getMessage());
        }
    }

    // Changement du paramètre de Integer à int
    @Override
    public void deleteOrder(int orderID) throws DeleteOrderException {
        try {
            Connection connection = SingletonConnection.getInstance();

            String sql = "DELETE FROM `Order` WHERE orderId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderID);

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DeleteOrderException(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Order> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<Order> orders = new ArrayList<>();

            String sql = "SELECT * FROM `Order` ORDER BY orderId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while(data.next()) {
                // Récupération et conversion des types SQL vers Java 8 Time (LocalTime & LocalDate)
                Date sqlDate = data.getDate("orderDate");
                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.now();

                Time sqlTime = data.getTime("hour");
                LocalTime localTime = (sqlTime != null) ? sqlTime.toLocalTime() : LocalTime.now();

                Order order = new Order(
                        data.getInt("orderId"),
                        localDate,
                        localTime,
                        data.getString("status"),
                        data.getInt("tableNumber"),
                        data.getInt("employeeId")
                );
                orders.add(order);
            }
            return orders;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }

    // Changement du paramètre de Integer à int
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
                Date sqlDate = data.getDate("orderDate");
                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.now();

                Time sqlTime = data.getTime("hour");
                LocalTime localTime = (sqlTime != null) ? sqlTime.toLocalTime() : LocalTime.now();

                order = new Order(
                        data.getInt("orderId"),
                        localDate,
                        localTime,
                        data.getString("status"),
                        data.getInt("tableNumber"),
                        data.getInt("employeeId")
                );
            }
            return order;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }
}