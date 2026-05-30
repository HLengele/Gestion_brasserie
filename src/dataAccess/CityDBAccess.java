package dataAccess;

import exception.ReadException;
import model.City;
import java.sql.*;
import java.util.ArrayList;

public class CityDBAccess implements CityDataAccess {

    @Override
    public ArrayList<City> readAll() throws ReadException {
        ArrayList<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM City ORDER BY id";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet data = statement.executeQuery()) {

            while (data.next()) {
                cities.add(new City(
                        data.getInt("id"),
                        data.getString("name"),
                        data.getString("postalCode")
                ));
            }
            return cities;
        } catch (Exception exception) {
            throw new ReadException("Error while reading cities: " + exception.getMessage());
        }
    }

    @Override
    public City readById(int id) throws ReadException {
        String sql = "SELECT * FROM City WHERE id = ?";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet data = statement.executeQuery()) {
                if (data.next()) {
                    return new City(
                            data.getInt("id"),
                            data.getString("name"),
                            data.getString("postalCode")
                    );
                }
            }
            return null;
        } catch (Exception exception) {
            throw new ReadException("Error while searching for city ID " + id + ": " + exception.getMessage());
        }
    }
}