package model;

import java.time.LocalDateTime;

public class ReservationSearchResult {
    private LocalDateTime date;
    private int nbPeople;
    private String customerName;
    private String cityName;
    private String postalCode;
    private int tableNumber;

    public ReservationSearchResult(LocalDateTime date, int nbPeople, String customerName,
                                   String cityName, String postalCode, int tableNumber) {
        this.date = date;
        this.nbPeople = nbPeople;
        this.customerName = customerName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getDate() { return date; }
    public int getNbPeople() { return nbPeople; }
    public String getCustomerName() { return customerName; }
    public String getCityName() { return cityName; }
    public String getPostalCode() { return postalCode; }
    public int getTableNumber() { return tableNumber; }
}