package model;

import exception.NullValueException;
import java.time.LocalDate;

public class Beer {
    private Integer beerId;
    private String name;
    private String color;
    private Double price;
    private String description;
    private Boolean containsAlcool;
    private LocalDate marketLaunchDate;
    private String comment;
    private Integer categoryId;

    public Beer(Integer beerId, String name, String color, Double price, String description,
                Boolean containsAlcool, LocalDate marketLaunchDate, String comment, Integer categoryId) throws NullValueException {
        this.beerId = beerId;
        setName(name);
        this.color = color;
        setPrice(price);
        this.description = description;
        this.containsAlcool = containsAlcool;
        this.marketLaunchDate = marketLaunchDate;
        this.comment = comment;
        this.categoryId = categoryId;
    }

    // Getters et Setters avec vérifications de base
    public Integer getBeerId() { return beerId; }
    public void setBeerId(Integer beerId) { this.beerId = beerId; }

    public String getName() { return name; }
    public void setName(String name) throws NullValueException {
        if (name == null || name.isBlank()) throw new NullValueException("The beer name is required.");
        this.name = name;
    }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) throws NullValueException {
        if (price == null || price < 0) throw new NullValueException("The price must be positive.");
        this.price = price;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getContainsAlcool() { return containsAlcool; }
    public void setContainsAlcool(Boolean containsAlcool) { this.containsAlcool = containsAlcool; }

    public LocalDate getMarketLaunchDate() { return marketLaunchDate; }
    public void setMarketLaunchDate(LocalDate marketLaunchDate) { this.marketLaunchDate = marketLaunchDate; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    @Override
    public String toString() { return name + " (" + color + ") - " + price + "€"; }
}