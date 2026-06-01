package controller;

import business.IBeerManager;
import business.ICategoryManager;
import business.IOrderManager;
import business.IReservationManager;
import exception.*;
import model.*;
import java.util.ArrayList;

public class ApplicationController implements IApplicationController {

    private IOrderManager orderManager;
    private IReservationManager reservationManager;
    private ICategoryManager categoryManager;
    private IBeerManager beerManager;

    public ApplicationController(IOrderManager orderManager, ICategoryManager categoryManager, IReservationManager reservationManager, IBeerManager beerManager) {
        this.orderManager = orderManager;
        this.categoryManager = categoryManager;
        this.reservationManager = reservationManager;
        this.beerManager = beerManager;
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