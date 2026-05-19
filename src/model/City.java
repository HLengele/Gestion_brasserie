package model;

import exception.NullValueException;

public class City {

    // ── Attributs du diagramme ─────────────────────────────────────────────────
    private Integer id;
    private String name;
    private String postalCode;

    // ── Constructeurs ──────────────────────────────────────────────────────────

    public City(Integer id, String name, String postalCode) throws NullValueException {
        setId(id);
        setName(name);
        setPostalCode(postalCode);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

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
            throw new NullValueException("Le nom de la ville ne peut pas être vide");
        }
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) throws NullValueException {
        if (postalCode != null && !postalCode.isBlank()) {
            this.postalCode = postalCode;
        } else {
            throw new NullValueException("Le code postal ne peut pas être vide");
        }
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return name + " (" + postalCode + ")";
    }
}