package model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate date;
    private int nbPeople;
    private Customer customer;
    private Table table;

    public Reservation(int id, LocalDate date, int nbPeople, Customer customer, Table table) {
        this.id = id;
        this.date = date;
        this.nbPeople = nbPeople;
        this.customer = customer;
        this.table = table;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNbPeople() {
        return nbPeople;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Table getTable() {
        return table;
    }
}