package controller;

import business.OrderManager;
import exception.*;
import model.*;

import java.util.ArrayList;

public class ApplicationController {

    private OrderManager orderManager;

    // ── Constructeur ───────────────────────────────────────────────────────────
    public ApplicationController() {
        setOrderManager(new OrderManager());
    }

    // ── Setter ─────────────────────────────────────────────────────────────────
    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    // ── Délégation : Beer (Bières) ─────────────────────────────────────────────

    public ArrayList<Beer> getAllBeers() throws ReadException {
        return orderManager.getAllBeers();
    }

    public Beer getBeerById(int id) throws ReadException {
        return orderManager.getBeerById(id);
    }

    // ── Délégation : City (Villes) ─────────────────────────────────────────────

    public ArrayList<City> getAllCities() throws ReadException {
        return orderManager.getAllCities();
    }

    public City getCityById(int id) throws ReadException {
        return orderManager.getCityById(id);
    }

    // ── Délégation : Employee (Employés) ───────────────────────────────────────

    public ArrayList<Employee> getAllEmployees() throws ReadException {
        return orderManager.getAllEmployees();
    }

    public Employee getEmployeeById(int id) throws ReadException {
        return orderManager.getEmployeeById(id);
    }

    // ── Délégation : Order (Commandes) ─────────────────────────────────────────

    public ArrayList<Order> getAllOrders() throws ReadException {
        return orderManager.getAllOrders();
    }

    public Order getOrderById(int id) throws ReadException {
        return orderManager.getOrderById(id);
    }

    public void addOrder(Order newOrder) throws AddOrderException {
        orderManager.addOrder(newOrder);
    }

    public void updateOrder(Order orderToUpdate) throws UpdateOrderException {
        orderManager.updateOrder(orderToUpdate);
    }

    public void deleteOrder(int orderID) throws DeleteOrderException {
        orderManager.deleteOrder(orderID);
    }

    // ── Délégation : Table (Tables) ────────────────────────────────────────────

    public ArrayList<Table> getAllTables() throws ReadException {
        return orderManager.getAllTables();
    }

    public Table getTableById(int id) throws ReadException {
        return orderManager.getTableById(id);
    }
    // ── À ajouter dans votre ApplicationController.java ────────────────────────

    public double calculateTableAddition(int tableNumber) throws ReadException {
        // Le contrôleur délègue directement le calcul à son manager interne
        return orderManager.calculateTableAddition(tableNumber);
    }
    public void addBeer(Beer beer) throws Exception {
        orderManager.addBeer(beer);
    }

    public void updateBeer(Beer beer) throws Exception {
        orderManager.updateBeer(beer);
    }

    public void deleteBeer(int beerId) throws Exception {
        orderManager.deleteBeer(beerId);
    }
}