package controller;

import business.OrderManager;
import business.CategoryManager;
import business.BeerManager;
import business.ReservationManager;
import exception.*;
import model.*;
import java.util.ArrayList;

public class ApplicationController {

    private OrderManager orderManager;
    private ReservationManager reservationManager;
    private CategoryManager categoryManager;
    private BeerManager beerManager;

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
    public void setBeerManager(BeerManager beerManager) { this.beerManager = beerManager; }
    public void setReservationManager(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }


    public ArrayList<Category> getAllCategories() throws ReadException {
        return categoryManager.getAllCategories();
    }

    public ArrayList<Beer> getAllBeers() throws ReadException {
        return beerManager.getAllBeers();
    }
    public ArrayList<Reservation> searchReservationsBetweenDates(java.time.LocalDate start, java.time.LocalDate end) throws ReadException {
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


    public int addOrder(Order newOrder) throws AddOrderException {
        return orderManager.addOrder(newOrder);
    }
    public void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
        orderManager.addLineOrder(orderId, beerId, quantity, realPrice);
    }

    public ArrayList<Table> getAllTables() throws ReadException { return orderManager.getAllTables(); }

    public double calculateTableAddition(int tableNumber) throws ReadException {
        return orderManager.calculateTableAddition(tableNumber);
    }
}