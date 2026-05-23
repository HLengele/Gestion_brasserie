package dataAccess;

import exception.ReadException;
import model.Category;
import java.util.ArrayList;

public interface CategoryDataAccess {
    ArrayList<Category> readAll() throws ReadException;
    Category readById(int id) throws ReadException;
}