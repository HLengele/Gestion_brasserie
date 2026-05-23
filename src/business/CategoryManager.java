package business;

import dataAccess.CategoryDBAccess;
import exception.ReadException;
import model.Category;
import java.util.ArrayList;

public class CategoryManager {

    private CategoryDBAccess categoryDBAccess;

    public CategoryManager() {
        // Le manager instancie sa classe d'accès aux données correspondante
        this.categoryDBAccess = new CategoryDBAccess();
    }

    public ArrayList<Category> getAllCategories() throws ReadException {
        // Appelle la méthode readAll() du DBAccess
        return categoryDBAccess.readAll();
    }
}