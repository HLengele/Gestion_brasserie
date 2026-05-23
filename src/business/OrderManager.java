package business;

import dataAccess.*;
import exception.*;
import model.*;

import java.sql.*;
import java.util.ArrayList;

public class OrderManager {

    // ── Déclarations des Data Access Objects (Interfaces) ──────────────────────
    private OrderDataAccess orderDao;
    private CityDataAccess cityDao;
    private EmployeeDataAccess employeeDao;
    private TableDataAccess tableDao;

    // ── Constructeur ───────────────────────────────────────────────────────────
    public OrderManager() {
        setOrderDao(new OrderDBAccess());
        setCityDao(new CityDBAccess());
        setEmployeeDao(new EmployeeDBAccess());
        setTableDao(new TableDBAccess());
    }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setOrderDao(OrderDataAccess orderDao) { this.orderDao = orderDao; }
    public void setCityDao(CityDataAccess cityDao) { this.cityDao = cityDao; }
    public void setEmployeeDao(EmployeeDataAccess employeeDao) { this.employeeDao = employeeDao; }
    public void setTableDao(TableDataAccess tableDao) { this.tableDao = tableDao; }

    // ── Méthodes COMMANDES ─────────────────────────────────────────────────────
    public ArrayList<Order> getAllOrders() throws ReadException { return orderDao.readAll(); }
    public Order getOrderById(int id) throws ReadException { return orderDao.readById(id); }
    public void updateOrder(Order orderToUpdate) throws UpdateOrderException { orderDao.updateOrder(orderToUpdate); }
    public void deleteOrder(int orderID) throws DeleteOrderException { orderDao.deleteOrder(orderID); }

    // ── Méthodes VILLES ────────────────────────────────────────────────────────
    public ArrayList<City> getAllCities() throws ReadException { return cityDao.readAll(); }
    public City getCityById(int id) throws ReadException { return cityDao.readById(id); }

    // ── Méthodes EMPLOYÉS ──────────────────────────────────────────────────────
    public ArrayList<Employee> getAllEmployees() throws ReadException { return employeeDao.readAll(); }
    public Employee getEmployeeById(int id) throws ReadException { return employeeDao.readById(id); }

    // ── Méthodes TABLES ────────────────────────────────────────────────────────
    public ArrayList<Table> getAllTables() throws ReadException { return tableDao.readAll(); }
    public Table getTableById(int id) throws ReadException { return tableDao.readById(id); }

    // ── Calcul de l'addition d'une table ───────────────────────────────────────
    public double calculateTableAddition(int tableNumber) throws ReadException {
        double total = 0.0;
        try {
            Connection connection = dataAccess.SingletonConnection.getInstance();

            String sqlOrders = "SELECT orderId FROM `Order` WHERE tableNumber = ?";
            PreparedStatement stmtOrders = connection.prepareStatement(sqlOrders);
            stmtOrders.setInt(1, tableNumber);
            ResultSet orders = stmtOrders.executeQuery();

            while (orders.next()) {
                int orderId = orders.getInt("orderId");

                String sqlLines = "SELECT quantity, realPrice FROM Line_Order WHERE orderId = ?";
                PreparedStatement stmtLines = connection.prepareStatement(sqlLines);
                stmtLines.setInt(1, orderId);
                ResultSet lines = stmtLines.executeQuery();

                while (lines.next()) {
                    int quantity     = lines.getInt("quantity");
                    double realPrice = lines.getDouble("realPrice");
                    total += quantity * realPrice;
                }
            }
        } catch (SQLException e) {
            throw new ReadException("Erreur lors du calcul de l'addition : " + e.getMessage());
        }

        return total;
    }
    // Modifier addOrder pour qu'il retourne le int
    public int addOrder(Order newOrder) throws AddOrderException {
        return orderDao.insertOrder(newOrder);
    }

    // Ajouter la méthode pour la ligne
    public void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
        orderDao.insertLineOrder(orderId, beerId, quantity, realPrice);
    }
}