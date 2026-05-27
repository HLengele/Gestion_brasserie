package model;

import exception.NullValueException;

public class Table {

    private int tableNumber;
    private int nbPlace;
    private String location;

    public Table(int tableNumber, int nbPlace, String location)
            throws NullValueException {
        setTableNumber(tableNumber);
        setNbPlace(nbPlace);
        setLocation(location);
    }


    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) throws NullValueException {
        if (tableNumber > 0) {
            this.tableNumber = tableNumber;
        } else {
            throw new NullValueException("The table number must be a positive integer");
        }
    }


    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) throws NullValueException {
        if (nbPlace > 0) {
            this.nbPlace = nbPlace;
        } else {
            throw new NullValueException("The table capacity must be a positive integer");
        }
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;   // null allowed
    }


    @Override
    public String toString() {
        return "Table n°" + tableNumber + " – " + nbPlace + " seats"
                + (location != null ? " (" + location + ")" : "");
    }
}