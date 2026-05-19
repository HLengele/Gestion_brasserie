package model;

import exception.NullValueException;

public class CommandLine {

    private Integer quantite;
    private Double realPrice; // Provient du schéma relationnel (prix au moment de la commande)
    private Integer orderId;
    private Integer beerId;

    // Si vous chargez l'objet Beer pour le calcul business :
    private Beer beer;

    public CommandLine(Integer quantite, Double realPrice, Integer orderId, Integer beerId) throws NullValueException {
        setQuantite(quantite);
        setRealPrice(realPrice);
        setOrderId(orderId);
        setBeerId(beerId);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) throws NullValueException {
        if (quantite != null && quantite > 0) {
            this.quantite = quantite;
        } else {
            throw new NullValueException("La quantité doit être supérieure à 0");
        }
    }

    public Double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBeerId() {
        return beerId;
    }

    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    // ── Méthode Métier (UML) ───────────────────────────────────────────────────

    /**
     * Calcule le sous-total de la ligne en fonction de la quantité et du prix réel.
     */
    public double calculerSousTotal() {
        if (realPrice != null && quantite != null) {
            return realPrice * quantite;
        }
        return 0.0;
    }
}