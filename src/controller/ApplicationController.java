package controller;

import business.OrderManager;
import business.CategoryManager;
import business.BeerManager; // <-- Nouvel import
import business.ReservationManager;
import exception.*;
import model.*;
import java.util.ArrayList;

public class ApplicationController {

    private OrderManager orderManager;
    private ReservationManager reservationManager;
    private CategoryManager categoryManager;
    private BeerManager beerManager; // <-- Nouvel attribut

    // ── Constructeur ───────────────────────────────────────────────────────────
    public ApplicationController() {
        setOrderManager(new OrderManager());
        setCategoryManager(new CategoryManager());
        setReservationManager(new ReservationManager());
        setBeerManager(new BeerManager()); // <-- Initialisation
    }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setOrderManager(OrderManager orderManager) { this.orderManager = orderManager; }
    public void setCategoryManager(CategoryManager categoryManager) { this.categoryManager = categoryManager; }
    public void setBeerManager(BeerManager beerManager) { this.beerManager = beerManager; } // <-- Setter
    public void setReservationManager(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }


    // ── Délégation : Category (Catégories) ─────────────────────────────────────
    public ArrayList<Category> getAllCategories() throws ReadException {
        return categoryManager.getAllCategories();
    }

    // ── Délégation : Beer (Bières) ─────────────────────────────────────────────
    // Ces méthodes pointent maintenant vers le beerManager !
    public ArrayList<Beer> getAllBeers() throws ReadException {
        return beerManager.getAllBeers();
    }
    // La méthode de délégation pour l'UI :
    public ArrayList<ReservationSearchResult> searchReservationsBetweenDates(java.time.LocalDate start, java.time.LocalDate end) throws ReadException {
        return reservationManager.searchReservationsBetweenDates(start, end);
    }

    public Beer getBeerById(int id) throws ReadException {
        return beerManager.getBeerById(id);
    }

    public void addBeer(Beer beer) throws Exception {
        beerManager.addBeer(beer);
    }

    public void updateBeer(Beer beer) throws Exception {
        beerManager.updateBeer(beer);
    }

    public void deleteBeer(int beerId) throws Exception {
        beerManager.deleteBeer(beerId);
    }

    // ── Délégation : City (Villes) ─────────────────────────────────────────────
    public ArrayList<City> getAllCities() throws ReadException { return orderManager.getAllCities(); }
    public City getCityById(int id) throws ReadException { return orderManager.getCityById(id); }

    // ── Délégation : Employee (Employés) ───────────────────────────────────────
    public ArrayList<Employee> getAllEmployees() throws ReadException { return orderManager.getAllEmployees(); }
    public Employee getEmployeeById(int id) throws ReadException { return orderManager.getEmployeeById(id); }

    // ── Délégation : Order (Commandes) ─────────────────────────────────────────
    public ArrayList<Order> getAllOrders() throws ReadException { return orderManager.getAllOrders(); }
    public Order getOrderById(int id) throws ReadException { return orderManager.getOrderById(id); }
    public int addOrder(Order newOrder) throws AddOrderException {
        return orderManager.addOrder(newOrder);
    }
    public void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
        orderManager.addLineOrder(orderId, beerId, quantity, realPrice);
    }
    public void updateOrder(Order orderToUpdate) throws UpdateOrderException { orderManager.updateOrder(orderToUpdate); }
    public void deleteOrder(int orderID) throws DeleteOrderException { orderManager.deleteOrder(orderID); }

    // ── Délégation : Table (Tables) ────────────────────────────────────────────
    public ArrayList<Table> getAllTables() throws ReadException { return orderManager.getAllTables(); }
    public Table getTableById(int id) throws ReadException { return orderManager.getTableById(id); }

    // ── Métier / Calculs ───────────────────────────────────────────────────────
    public double calculateTableAddition(int tableNumber) throws ReadException {
        return orderManager.calculateTableAddition(tableNumber);
    }
    // ── Tâche Métier : Prise de Commande Complète ─────────────────────────────
    public void processOrder(int tableNumber, int beerId, int quantity) throws Exception {
        // 1. Récupérer la bière pour obtenir son prix actuel
        Beer beer = beerManager.getBeerById(beerId);
        if (beer == null) {
            throw new Exception("La bière sélectionnée n'existe pas.");
        }
        double currentPrice = beer.getPrice();

        // 2. Créer l'entête de la commande (L'ID 0 sera ignoré par l'AUTO_INCREMENT)
        Order newOrder = new Order(0, java.time.LocalTime.now(), tableNumber);
        int newOrderId = orderManager.addOrder(newOrder);

        // 3. Lier la ligne de commande avec le prix figé à l'instant T
        orderManager.addLineOrder(newOrderId, beerId, quantity, currentPrice);
    }
}