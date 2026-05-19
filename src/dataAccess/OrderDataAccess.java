package dataAccess;

import exception.*;
import model.Order;
import java.util.ArrayList;

public interface OrderDataAccess {
    void insertOrder(Order newOrder) throws AddOrderException;
    void updateOrder(Order orderToUpdate) throws UpdateOrderException;
    void deleteOrder(int orderID) throws DeleteOrderException; // Mis en int primitif
    ArrayList<Order> readAll() throws ReadException;
    Order readById(int id) throws ReadException; // Mis en int primitif
}