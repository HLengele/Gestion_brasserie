package dataAccess;

import exception.*;
import model.Beer;
import java.util.ArrayList;

public interface BeerDataAccess {
    void insertBeer(Beer beer) throws Exception;
    ArrayList<Beer> readAll() throws ReadException;
    Beer readById(int id) throws ReadException;
    void updateBeer(Beer beer) throws Exception;
    void deleteBeer(int beerId) throws Exception;
}