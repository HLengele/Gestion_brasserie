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
        setQuantity(quantity);
        setRealPrice(realPrice);
    }

    public int getQuantity() { return quantity; }
    public double getRealPrice() { return realPrice; }
    public int getBeerId() { return beerId; }
    public String getBeerName() { return beerName; }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("The quantity must be greater than zero.");
        this.quantity = quantity;
    }
}