package business;

import dataAccess.*;
import exception.*;
import model.*;

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
        // Initialisation de tous les accès à la base de données
        setOrderDao(new OrderDBAccess());
        setBeerDao(new BeerDBAccess());
        setCityDao(new CityDBAccess());
        setEmployeeDao(new EmployeeDBAccess());
        setTableDao(new TableDBAccess());
    }

    // ── Setters pour l'injection/modification des DAOs ────────────────────────
    public void setOrderDao(OrderDataAccess orderDao) {
        this.orderDao = orderDao;
    }

    public void setBeerDao(BeerDataAccess beerDao) {
        this.beerDao = beerDao;
    }

    public void setCityDao(CityDataAccess cityDao) {
        this.cityDao = cityDao;
    }

    public void setEmployeeDao(EmployeeDataAccess employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setTableDao(TableDataAccess tableDao) {
        this.tableDao = tableDao;
    }

    // ── Méthodes concernant les COMMANDES (Order) ──────────────────────────────

    public ArrayList<Order> getAllOrders() throws ReadException {
        return orderDao.readAll();
    }

    public Order getOrderById(int id) throws ReadException {
        return orderDao.readById(id);
    }

    public void addOrder(Order newOrder) throws AddOrderException {
        orderDao.insertOrder(newOrder);
    }

    public void updateOrder(Order orderToUpdate) throws UpdateOrderException {
        orderDao.updateOrder(orderToUpdate);
    }

    public void deleteOrder(int orderID) throws DeleteOrderException {
        orderDao.deleteOrder(orderID);
    }

    // ── Méthodes concernant les BIÈRES (Beer) ──────────────────────────────────

    public ArrayList<Beer> getAllBeers() throws ReadException {
        return beerDao.readAll();
    }

    public Beer getBeerById(int id) throws ReadException {
        return beerDao.readById(id);
    }

    // ── Méthodes concernant les VILLES (City) ──────────────────────────────────

    public ArrayList<City> getAllCities() throws ReadException {
        return cityDao.readAll();
    }

    public City getCityById(int id) throws ReadException {
        return cityDao.readById(id);
    }

    // ── Méthodes concernant les EMPLOYÉS (Employee) ────────────────────────────

    public ArrayList<Employee> getAllEmployees() throws ReadException {
        return employeeDao.readAll();
    }

    public Employee getEmployeeById(int id) throws ReadException {
        return employeeDao.readById(id);
    }

    // ── Méthodes concernant les TABLES (Table) ─────────────────────────────────

    public ArrayList<Table> getAllTables() throws ReadException {
        return tableDao.readAll();
    }

    public Table getTableById(int id) throws ReadException {
        return tableDao.readById(id);
    }
    public double calculateTableAddition(int tableNumber) throws exception.ReadException {
        double total = 0.0;

        // Récupération de toutes les commandes via votre DAO d'accès aux données
        ArrayList<model.Order> allOrders = orderDao.readAll();

        for (model.Order order : allOrders) {
            // Si la commande correspond à la table recherchée et n'est pas encore clôturée
            if (order.getTableNumber() == tableNumber && !"Payée".equalsIgnoreCase(order.getStatus())) {
                // Logique de cumul des montants à adapter selon la structure de vos prix de bières
                // total += (calcul...)
            }
        }
        return total;
    }
}