package dataAccess;

import exception.ReadException;
import model.City;
import java.sql.*;
import java.util.ArrayList;

public class CityDBAccess implements CityDataAccess {

    @Override
    public ArrayList<City> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<City> cities = new ArrayList<>();

            String sql = "SELECT * FROM City ORDER BY id";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while (data.next()) {
                City city = new City(
                        data.getInt("id"),
                        data.getString("name"),
                        data.getString("postalCode")
                );
                cities.add(city);
            }
            return cities;
        } catch (Exception exception) { // Utilisation de Exception pour capturer SQLException et NullValueException
            throw new ReadException(exception.getMessage());
        }
    }

    @Override
    public City readById(int id) throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            City city = null;

            String sql = "SELECT * FROM City WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet data = statement.executeQuery();

            if (data.next()) {
                city = new City(
                        data.getInt("id"),
                        data.getString("name"),
                        data.getString("postalCode")
                );
            }
            return city;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }
}