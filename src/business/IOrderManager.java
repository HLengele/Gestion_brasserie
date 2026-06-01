package business;

import exception.AddOrderException;
import exception.ReadException;
import model.Order;
import model.Table;
import java.util.ArrayList;

public interface IOrderManager {
    int addOrder(Order newOrder) throws AddOrderException;
    void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception;
    ArrayList<Table> getAllTables() throws ReadException;
    double calculateTableAddition(int tableNumber) throws ReadException;
}