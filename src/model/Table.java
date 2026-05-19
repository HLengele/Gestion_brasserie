package model;

import exception.NullValueException;

public class Table {

    private int tableNumber;   // PK – correspond à tableNumber en BD
    private int nbPlace;       // NOT NULL CHECK > 0
    private String location;   // nullable

    public Table(int tableNumber, int nbPlace, String location)
            throws NullValueException {
        setTableNumber(tableNumber);
        setNbPlace(nbPlace);
        setLocation(location);
    }

    // ── tableNumber ────────────────────────────────────────────────────────────

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) throws NullValueException {
        if (tableNumber > 0) {
            this.tableNumber = tableNumber;
        } else {
            throw new NullValueException("Le numéro de table doit être un entier positif");
        }
    }

    // ── nbPlace ────────────────────────────────────────────────────────────────

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) throws NullValueException {
        if (nbPlace > 0) {
            this.nbPlace = nbPlace;
        } else {
            throw new NullValueException("La capacité de la table doit être un entier positif");
        }
    }

    // ── location (nullable) ────────────────────────────────────────────────────

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;   // null autorisé
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Table n°" + tableNumber + " – " + nbPlace + " places"
                + (location != null ? " (" + location + ")" : "");
    }
}