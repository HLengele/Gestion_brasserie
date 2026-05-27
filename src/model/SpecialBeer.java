package model;

import exception.NullValueException;
import java.time.LocalDate;

public class SpecialBeer extends Beer {

    private String origin;

    public SpecialBeer(Integer beerId, String name, String color, Double price, String description,
                       Boolean containsAlcool, LocalDate marketLaunchDate, String comment, Integer categoryId,
                       String origin) throws NullValueException {

        super(beerId, name, color, price, description, containsAlcool, marketLaunchDate, comment, categoryId);

        setOrigin(origin);
    }


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }


    @Override
    public String toString() {
        return super.toString() + " [Origine : " + origin + "]";
    }
}