package business;

import exception.ReadException;
import model.Category;
import java.util.ArrayList;

public interface ICategoryManager {
    ArrayList<Category> getAllCategories() throws ReadException;
}