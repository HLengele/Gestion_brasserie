package model;

import exception.NullValueException;

public class City {

    private Integer id;
    private String name;
    private String postalCode;


    public City(Integer id, String name, String postalCode) throws NullValueException {
        setId(id);
        setName(name);
        setPostalCode(postalCode);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NullValueException {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new NullValueException("The city name cannot be empty");
        }
    }

    public void setPostalCode(String postalCode) throws NullValueException {
        if (postalCode != null && !postalCode.isBlank()) {
            this.postalCode = postalCode;
        } else {
            throw new NullValueException("The postal code cannot be empty");
        }
    }


    @Override
    public String toString() {
        return name + " (" + postalCode + ")";
    }
}