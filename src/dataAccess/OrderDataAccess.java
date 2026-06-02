package dataAccess;

import exception.AddOrderException;
import model.LineOrder;
import model.Order;
import java.util.ArrayList;
import exception.ReadException;


public interface OrderDataAccess {
    int insertOrder(Order newOrder) throws AddOrderException;

}