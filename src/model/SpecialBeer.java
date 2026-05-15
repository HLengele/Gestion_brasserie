package model;

import exception.Nullvalueexception;

public class SpecialBeer extends Beer {

    private String origin;   // ex : "Belgique", "Allemagne", "Tchéquie"…

    public SpecialBeer(Integer beerId, String nom, Double price, String type, String origin)
            throws Nullvalueexception {
        super(beerId, nom, price, type);
        setOrigin(origin);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return super.toString() + " [Origine : " + origin + "]";
    }
}