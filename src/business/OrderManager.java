package business;

import dataAccess.*;
import exception.*;
import model.*;

import java.sql.*;
import java.util.ArrayList;

public class OrderManager {

    // ── Déclarations des Data Access Objects (Interfaces) ──────────────────────
    private OrderDataAccess orderDao;
    private BeerDataAccess beerDao;
    private CityDataAccess cityDao;
    private EmployeeDataAccess employeeDao;
    private TableDataAccess tableDao;

    // ── Constructeur ───────────────────────────────────────────────────────────
    public OrderManager() {
        setOrderDao(new OrderDBAccess());
        setBeerDao(new BeerDBAccess());
        setCityDao(new CityDBAccess());
        setEmployeeDao(new EmployeeDBAccess());
        setTableDao(new TableDBAccess());
    }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setOrderDao(OrderDataAccess orderDao) { this.orderDao = orderDao; }
    public void setBeerDao(BeerDataAccess beerDao) { this.beerDao = beerDao; }
    public void setCityDao(CityDataAccess cityDao) { this.cityDao = cityDao; }
    public void setEmployeeDao(EmployeeDataAccess employeeDao) { this.employeeDao = employeeDao; }
    public void setTableDao(TableDataAccess tableDao) { this.tableDao = tableDao; }

    // ── Méthodes COMMANDES ─────────────────────────────────────────────────────
    public ArrayList<Order> getAllOrders() throws ReadException { return orderDao.readAll(); }
    public Order getOrderById(int id) throws ReadException { return orderDao.readById(id); }
    public void addOrder(Order newOrder) throws AddOrderException { orderDao.insertOrder(newOrder); }
    public void updateOrder(Order orderToUpdate) throws UpdateOrderException { orderDao.updateOrder(orderToUpdate); }
    public void deleteOrder(int orderID) throws DeleteOrderException { orderDao.deleteOrder(orderID); }

    // ── Méthodes BIÈRES ────────────────────────────────────────────────────────
    public ArrayList<Beer> getAllBeers() throws ReadException { return beerDao.readAll(); }
    public Beer getBeerById(int id) throws ReadException { return beerDao.readById(id); }

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

            // On cherche toutes les commandes non payées pour cette table
            String sqlOrders = "SELECT orderId FROM `Order` WHERE tableNumber = ? AND status != 'Payée'";
            PreparedStatement stmtOrders = connection.prepareStatement(sqlOrders);
            stmtOrders.setInt(1, tableNumber);
            ResultSet orders = stmtOrders.executeQuery();

            // Pour chaque commande on additionne les lignes de commande
            while (orders.next()) {
                int orderId = orders.getInt("orderId");

                String sqlLines = "SELECT quantity, unit_price FROM Order_Line WHERE orderId = ?";
                PreparedStatement stmtLines = connection.prepareStatement(sqlLines);
                stmtLines.setInt(1, orderId);
                ResultSet lines = stmtLines.executeQuery();

                while (lines.next()) {
                    int quantity      = lines.getInt("quantity");
                    double unit_price = lines.getDouble("unit_price");
                    total += quantity * unit_price;
                }
            }

        } catch (SQLException e) {
            throw new ReadException("Erreur lors du calcul de l'addition : " + e.getMessage());
        }

        return total;
    }
}