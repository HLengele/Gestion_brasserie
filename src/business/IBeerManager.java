package business;

import exception.ReadException;
import model.Beer;
import java.util.ArrayList;

public interface IBeerManager {
    ArrayList<Beer> getAllBeers() throws ReadException;
    Beer getBeerById(int id) throws ReadException;
    void addBeer(Beer beer) throws Exception;
    void updateBeer(Beer beer) throws Exception;
    void deleteBeer(int beerId) throws Exception;
}