package dataAccess;

import exception.ReadException;
import model.Table;
import java.sql.*;
import java.util.ArrayList;

public class TableDBAccess implements TableDataAccess {

    @Override
    public ArrayList<Table> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<Table> tables = new ArrayList<>();

            String sql = "SELECT * FROM `Table` ORDER BY tableNumber";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while (data.next()) {
                String location = data.getString("location");
                if (data.wasNull()) location = null;

                // Utilisation stricte des types primitifs int
                int tableNumber = data.getInt("tableNumber");
                int nbPlace = data.getInt("nbPlace");

                Table table = new Table(tableNumber, nbPlace, location);
                tables.add(table);
            }
            return tables;

        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }

    @Override
    public Table readById(int id) throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();

            String sql = "SELECT * FROM `Table` WHERE tableNumber = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet data = statement.executeQuery();

            if (data.next()) {
                String location = data.getString("location");
                if (data.wasNull()) location = null;

                // Utilisation stricte des types primitifs int
                int tableNumber = data.getInt("tableNumber");
                int nbPlace = data.getInt("nbPlace");

                return new Table(tableNumber, nbPlace, location);
            }
            return null;

        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }
}