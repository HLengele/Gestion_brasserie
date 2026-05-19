package model;

public class Category {
    private int id;
    private String name;

    public Category(int id, String name) {
        setId(id);
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
