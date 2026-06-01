package controller;

import exception.AddOrderException;
import exception.ReadException;
import model.Beer;
import model.Category;
import model.Order;
import model.Reservation;
import model.Table;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IApplicationController {
    ArrayList<Category> getAllCategories() throws ReadException;
    ArrayList<Beer> getAllBeers() throws ReadException;
    ArrayList<Reservation> searchReservationsBetweenDates(LocalDate start, LocalDate end) throws ReadException;
    Beer getBeerById(int id) throws ReadException;
    void addBeer(Beer beer) throws Exception;
    void updateBeer(Beer beer) throws Exception;
    void deleteBeer(int beerId) throws Exception;
    int addOrder(Order newOrder) throws AddOrderException;
    void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception;
    ArrayList<Table> getAllTables() throws ReadException;
    double calculateTableAddition(int tableNumber) throws ReadException;
}