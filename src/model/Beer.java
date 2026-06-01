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

    private Category category;

    public Beer(Integer beerId, String name, String color, Double price, String description,
                Boolean containsAlcool, LocalDate marketLaunchDate, String comment, Category category) throws NullValueException {
        setBeerId(beerId);
        setName(name);
        setColor(color);
        setPrice(price);
        setMarketLaunchDate(marketLaunchDate);
        setContainsAlcool(containsAlcool);
        setCategory(category);
        this.description = description;
        this.comment = comment;
    }

    public Integer getBeerId() { return beerId; }
    public void setBeerId(Integer beerId) { this.beerId = beerId; }

    public String getName() { return name; }
    public void setName(String name) throws NullValueException {
        if (name == null || name.isBlank()) throw new NullValueException("The beer name is required.");
        this.name = name;
    }

    public String getColor() { return color; }

    public Double getPrice() { return price; }

    public void setColor(String color) throws NullValueException {
        if (color != null && !color.isBlank()) {
            this.color = color;
        } else {
            throw new NullValueException("The beer color cannot be empty.");
        }
    }

    public void setContainsAlcool(Boolean containsAlcool) throws NullValueException {
        if (containsAlcool != null) {
            this.containsAlcool = containsAlcool;
        } else {
            throw new NullValueException("Please specify if the beer contains alcohol (true or false).");
        }
    }

    public void setMarketLaunchDate(LocalDate marketLaunchDate) throws IllegalArgumentException {
        if (marketLaunchDate != null && marketLaunchDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The market launch date cannot be in the future.");
        }
        this.marketLaunchDate = marketLaunchDate;
    }

    public void setPrice(Double price) throws NullValueException {
        if (price == null || price < 0) throw new NullValueException("The price must be positive.");
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getContainsAlcool() {
        return containsAlcool;
    }

    public LocalDate getMarketLaunchDate() {
        return marketLaunchDate;
    }

    public String getComment() {
        return comment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        String catName = (category != null) ? category.getName() : "Sans catégorie";
        return name + " (" + color + ") [" + catName + "] - " + price + "€";
    }
}