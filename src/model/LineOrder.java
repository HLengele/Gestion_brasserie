package model;

public class LineOrder {
    private Integer id;
    private int quantity;
    private double realPrice;
    private int orderId;
    private int beerId;

    private String beerName;

    public LineOrder(int beerId, String beerName, int quantity, double realPrice) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.quantity = quantity;
        this.realPrice = realPrice;
    }

    public int getQuantity() { return quantity; }
    public double getRealPrice() { return realPrice; }
    public int getBeerId() { return beerId; }
    public String getBeerName() { return beerName; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}