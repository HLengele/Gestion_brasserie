package dataAccess;

import exception.*;
import model.Order;
import java.util.ArrayList;

public interface OrderDataAccess {
    int insertOrder(Order newOrder) throws AddOrderException; // Doit retourner un 'int'
    void insertLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception;
    void deleteOrder(int orderID) throws DeleteOrderException; // Mis en int primitif
    ArrayList<Order> readAll() throws ReadException;
    void updateOrder(Order order) throws UpdateOrderException;
    Order readById(int id) throws ReadException; // Mis en int primitif
}