package model;

import exception.NullValueException;

public class Beer {

    private Integer beerId;
    private String  name;
    private Double  price;
    private String  type;

    public Beer(Integer beerId, String name, Double price, String type)
            throws NullValueException {
        setBeerId(beerId);
        setName(name);
        setPrice(price);
        setType(type);
    }

    public Integer getBeerId() { return beerId; }
    public void setBeerId(Integer beerId) { this.beerId = beerId; }

    public String getName() { return name; }
    public void setName(String nom) throws NullValueException {
        if (nom != null && !nom.isBlank())
            this.name = nom; // ✅ CORRECTION : était "this.name = name" (bug)
        else
            throw new NullValueException("Le nom de la bière ne peut pas être vide");
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) throws NullValueException {
        if (price != null && price >= 0.0)
            this.price = price;
        else
            throw new NullValueException("Le prix doit être positif ou nul");
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return name + " (" + type + ") – " + price + " €";
    }
}