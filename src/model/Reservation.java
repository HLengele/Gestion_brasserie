package model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate date;
    private int nbPeople;
    private Table table;

    private Customer customer;

    public Reservation(int id, LocalDate date, int nbPeople, Customer customer, Table table) {
        this.id = id;
        this.date = date;
        this.nbPeople = nbPeople;
        this.customer = customer;
        this.table = table;
    }

    public void setNbPeople(int nbPeople) {
        if (nbPeople <= 0) throw new IllegalArgumentException("A reservation must be for at least 1 person.");
        this.nbPeople = nbPeople;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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