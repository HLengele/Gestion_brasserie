package model;

import exception.NullValueException;

public class Category {
    private int categoryId;
    private String name;

    public Category(int categoryId, String name) throws NullValueException {
        this.categoryId = categoryId;
        setName(name); // On passe par le setter pour activer la vérification
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NullValueException {
        // Le filtre de vérification !
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new NullValueException("Le nom de la catégorie ne peut pas être vide.");
        }
    }

    // Très important pour l'affichage dans la ComboBox de ton CRUD !
    @Override
    public String toString() {
        return name;
    }
}