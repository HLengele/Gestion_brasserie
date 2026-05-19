package dataAccess;

import exception.ReadException;
import model.Table;
import java.util.ArrayList;

public interface TableDataAccess {
    ArrayList<Table> readAll() throws ReadException;
    Table readById(int id) throws ReadException;
}