package dataAccess;

import exception.*;
import model.Beer;
import java.util.ArrayList;

public interface BeerDataAccess {
    void insertBeer(Beer beer) throws Exception;       // CREATE
    ArrayList<Beer> readAll() throws ReadException;    // READ ALL
    Beer readById(int id) throws ReadException;        // READ BY ID
    void updateBeer(Beer beer) throws Exception;       // UPDATE
    void deleteBeer(int beerId) throws Exception;      // DELETE
}