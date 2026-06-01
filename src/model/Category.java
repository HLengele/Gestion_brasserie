package model;

import exception.NullValueException;

public class Category {
    private int categoryId;
    private String name;

    public Category(int categoryId, String name) throws NullValueException {
        this.categoryId = categoryId;
        setName(name);
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
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new NullValueException("The category name cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}