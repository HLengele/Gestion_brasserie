package model;

import exception.NullValueException;
import java.time.LocalDate;

public class SpecialBeer extends Beer {

    private String origin; // ex : "Belgique", "Allemagne", "Tchéquie"…

    // 1. Le constructeur demande TOUS les paramètres de Beer + le paramètre spécifique 'origin'
    public SpecialBeer(Integer beerId, String name, String color, Double price, String description,
                       Boolean containsAlcool, LocalDate marketLaunchDate, String comment, Integer categoryId,
                       String origin) throws NullValueException {

        // 2. On passe les 9 premiers paramètres à la classe parente (Beer)
        super(beerId, name, color, price, description, containsAlcool, marketLaunchDate, comment, categoryId);

        // 3. On initialise l'attribut propre à SpecialBeer
        setOrigin(origin);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return super.toString() + " [Origine : " + origin + "]";
    }
}