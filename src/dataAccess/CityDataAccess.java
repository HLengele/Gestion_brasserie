package dataAccess;

import exception.ReadException;
import model.City;
import java.util.ArrayList;

public interface CityDataAccess {
    ArrayList<City> readAll() throws ReadException;
    City readById(int id) throws ReadException;
}