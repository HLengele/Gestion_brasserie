package dataAccess;

import exception.ReadException;
import model.LineOrder;
import java.util.ArrayList;

public interface LineOrderDataAccess {
    void insertLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception;
    ArrayList<LineOrder> getLineOrdersByTable(int tableNumber) throws ReadException;
}