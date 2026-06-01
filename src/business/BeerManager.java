package business;

import dataAccess.BeerDataAccess; // On importe l'interface, pas le DBAccess !
import exception.ReadException;
import model.Beer;
import java.util.ArrayList;

public class BeerManager implements IBeerManager {

    private BeerDataAccess beerDao;

    public BeerManager(BeerDataAccess beerDao) {
        this.beerDao = beerDao;
    }

    public ArrayList<Beer> getAllBeers() throws ReadException {
        return beerDao.readAll();
    }

    public Beer getBeerById(int id) throws ReadException {
        return beerDao.readById(id);
    }

    public void addBeer(Beer beer) throws Exception {
        beerDao.insertBeer(beer);
    }

    public void updateBeer(Beer beer) throws Exception {
        beerDao.updateBeer(beer);
    }

    public void deleteBeer(int beerId) throws Exception {
        beerDao.deleteBeer(beerId);
    }
}