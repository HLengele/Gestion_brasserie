package dataAccess;

import exception.AddOrderException;
import model.Order;
import java.util.ArrayList;
import exception.ReadException;


public interface OrderDataAccess {
    int insertOrder(Order newOrder) throws AddOrderException;
    void insertLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception;

    double getTotalPriceByTable(int tableNumber) throws ReadException;
}