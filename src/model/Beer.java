package model;

import exception.Nullvalueexception;

public class Beer {

    private Integer beerId;
    private String  nom;
    private Double  price;
    private String  type;       // ex : IPA, Stout, Blonde, Trappiste…

    public Beer(Integer beerId, String nom, Double price, String type)
            throws Nullvalueexception {
        setBeerId(beerId);
        setNom(nom);
        setPrice(price);
        setType(type);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getBeerId() { return beerId; }
    public void setBeerId(Integer beerId) { this.beerId = beerId; }

    public String getNom() { return nom; }
    public void setNom(String nom) throws Nullvalueexception {
        if (nom != null && !nom.isBlank())
            this.nom = nom;
        else
            throw new Nullvalueexception("Le nom de la bière ne peut pas être vide");
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) throws Nullvalueexception {
        if (price != null && price >= 0.0)
            this.price = price;
        else
            throw new Nullvalueexception("Le prix doit être positif ou nul");
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return nom + " (" + type + ") – " + price + " €";
    }
}