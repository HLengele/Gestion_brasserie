package dataAccess;

import exception.*;
import model.Order;
import java.util.ArrayList;

public interface OrderDataAccess {
    ArrayList<Order> readAll() throws ReadException;
    Order readById(int id) throws ReadException;
    double getTotalPriceByTable(int tableNumber) throws ReadException;
}