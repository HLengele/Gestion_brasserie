package model;

public class LineOrder {
    private Integer id; // Généré par la BD plus tard
    private int quantity;
    private double realPrice;
    private int orderId; // Sera défini au moment de l'enregistrement
    private int beerId;

    // Attributs purement utilitaires pour l'affichage dans le panier (Interface Graphique)
    private String beerName;

    // Constructeur pour le panier (avant l'insertion en BD)
    public LineOrder(int beerId, String beerName, int quantity, double realPrice) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.quantity = quantity;
        this.realPrice = realPrice;
    }

    // Getters
    public int getQuantity() { return quantity; }
    public double getRealPrice() { return realPrice; }
    public int getBeerId() { return beerId; }
    public String getBeerName() { return beerName; }

    // Setters (si besoin de modifier la quantité dans le panier)
    public void setQuantity(int quantity) { this.quantity = quantity; }
}