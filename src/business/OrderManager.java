package business;

import dataAccess.*;
import exception.*;
import model.*;
import java.util.ArrayList;

public class OrderManager implements IOrderManager {

    private OrderDataAccess orderDao;
    private LineOrderDataAccess lineOrderDao;
    private CityDataAccess cityDao;
    private EmployeeDataAccess employeeDao;
    private TableDataAccess tableDao;

    public OrderManager(OrderDataAccess orderDao, LineOrderDataAccess lineOrderDao,
                        CityDataAccess cityDao, EmployeeDataAccess employeeDao,
                        TableDataAccess tableDao) {
        this.orderDao     = orderDao;
        this.lineOrderDao = lineOrderDao;
        this.cityDao      = cityDao;
        this.employeeDao  = employeeDao;
        this.tableDao     = tableDao;
    }


    public int addOrder(Order newOrder) throws AddOrderException {
        return orderDao.insertOrder(newOrder);
    }

    public void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
        if (orderId <= 0 || beerId <= 0) {
            throw new IllegalArgumentException("The order and beer IDs must be valid.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity must be greater than zero.");
        }
        if (realPrice < 0) {
            throw new IllegalArgumentException("The actual price cannot be negative.");
        }
        lineOrderDao.insertLineOrder(orderId, beerId, quantity, realPrice);
    }

    public ArrayList<Table> getAllTables() throws ReadException {
        return tableDao.readAll();
    }

    public double calculateTableAddition(int tableNumber) throws ReadException {
        if (tableNumber <= 0) {
            throw new IllegalArgumentException("The table number must be greater than 0.");
        }
        ArrayList<LineOrder> lineOrders = lineOrderDao.getLineOrdersByTable(tableNumber);
        return calculateCartTotal(lineOrders);
    }

    public double calculateCartTotal(ArrayList<LineOrder> cart) {
        if (cart == null || cart.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (LineOrder line : cart) {
            total += line.getQuantity() * line.getRealPrice();
        }
        return total;
    }
}