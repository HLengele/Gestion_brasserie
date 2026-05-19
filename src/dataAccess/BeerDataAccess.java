package dataAccess;

import exception.ReadException;
import model.Beer;
import java.util.ArrayList;

// Une interface utilise le mot-clé "interface" et non "class"
public interface BeerDataAccess {
    ArrayList<Beer> readAll() throws ReadException;
    Beer readById(int id) throws ReadException;
}