package dataAccess;

import exception.*;
import model.Order;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {

    @Override
    public void insertOrder(Order newOrder) throws AddOrderException {
        try {
            Connection connection = SingletonConnection.getInstance();
            String sql = "INSERT INTO `Order` (hour, tableNumber) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setTime(1, Time.valueOf(newOrder.getHour()));
            statement.setInt(2, newOrder.getTableNumber());

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new AddOrderException(exception.getMessage());
        }
    }

    @Override
    public void updateOrder(Order orderToUpdate) throws UpdateOrderException {
        try {
            Connection connection = SingletonConnection.getInstance();
            String sql = "UPDATE `Order` SET hour = ?, tableNumber = ? WHERE orderId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setTime(1, Time.valueOf(orderToUpdate.getHour()));
            statement.setInt(2, orderToUpdate.getTableNumber());
            statement.setInt(3, orderToUpdate.getOrderId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new UpdateOrderException(exception.getMessage());
        }
    }

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
}