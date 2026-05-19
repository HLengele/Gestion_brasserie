package dataAccess;



import exception.ReadException;
import model.Beer;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDataAccess {
    ArrayList<Beer> readAll() throws ReadException;
    Beer readById(int id) throws ReadException;
}
