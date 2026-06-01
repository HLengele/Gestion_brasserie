package business;

import dataAccess.CategoryDataAccess; // Utiliser l'interface !
import exception.ReadException;
import model.Category;
import java.util.ArrayList;

public class CategoryManager implements ICategoryManager {

    private CategoryDataAccess categoryDataAccess;

    public CategoryManager(CategoryDataAccess categoryDataAccess) {
        this.categoryDataAccess = categoryDataAccess;
    }

    public ArrayList<Category> getAllCategories() throws ReadException {
        return categoryDataAccess.readAll();
    }
}