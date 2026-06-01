package dataAccess;

import exception.ReadException;
import model.Beer;
import model.Category; // N'oublie pas l'import !
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class BeerDBAccess implements BeerDataAccess {

    @Override
    public void insertBeer(Beer beer) throws Exception {
        if (beer == null) {
            throw new Exception("DAO Error: Attempt to insert a null beer into the database.");
        }else{
            String sql = "INSERT INTO Beer (name, color, price, description, containsAlcool, marketLaunchDate, comment, categoryId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = SingletonConnection.getInstance();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, beer.getName());
                statement.setString(2, beer.getColor());
                statement.setDouble(3, beer.getPrice());
                statement.setString(4, beer.getDescription());
                statement.setBoolean(5, beer.getContainsAlcool() != null ? beer.getContainsAlcool() : true);
                statement.setDate(6, beer.getMarketLaunchDate() != null ? Date.valueOf(beer.getMarketLaunchDate()) : null);
                statement.setString(7, beer.getComment());

                // MODIFICATION : On va chercher l'ID dans l'objet Category s'il existe
                if (beer.getCategory() != null) {
                    statement.setInt(8, beer.getCategory().getCategoryId());
                } else {
                    statement.setNull(8, Types.INTEGER);
                }

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new Exception("Error while adding the beer : " + e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<Beer> readAll() throws ReadException {
        ArrayList<Beer> beers = new ArrayList<>();

        String sql = "SELECT b.*, c.id AS catId, c.name AS catName " +
                "FROM Beer b " +
                "LEFT JOIN Category c ON b.categoryId = c.id " +
                "ORDER BY b.name";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet data = statement.executeQuery()) {

            while (data.next()) {
                Date sqlDate = data.getDate("marketLaunchDate");
                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                // MODIFICATION : Instanciation de la Category si elle n'est pas nulle
                int catId = data.getInt("catId");
                Category category = null;
                if (!data.wasNull()) {
                    category = new Category(catId, data.getString("catName"));
                }

                Beer beer = new Beer(
                        data.getInt("beerId"),
                        data.getString("name"),
                        data.getString("color"),
                        data.getDouble("price"),
                        data.getString("description"),
                        data.getBoolean("containsAlcool"),
                        localDate,
                        data.getString("comment"),
                        category
                );
                beers.add(beer);
            }
        } catch (Exception e) {
            throw new ReadException("Error reading beers : " + e.getMessage());
        }
        return beers;
    }

    @Override
    public Beer readById(int id) throws ReadException {
        // MODIFICATION : LEFT JOIN ici aussi
        String sql = "SELECT b.*, c.id AS catId, c.name AS catName " +
                "FROM Beer b " +
                "LEFT JOIN Category c ON b.categoryId = c.id " +
                "WHERE b.beerId = ?";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet data = statement.executeQuery()) {
                if (data.next()) {
                    Date sqlDate = data.getDate("marketLaunchDate");
                    LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    int catId = data.getInt("catId");
                    Category category = null;
                    if (!data.wasNull()) {
                        category = new Category(catId, data.getString("catName"));
                    }

                    return new Beer(
                            data.getInt("beerId"),
                            data.getString("name"),
                            data.getString("color"),
                            data.getDouble("price"),
                            data.getString("description"),
                            data.getBoolean("containsAlcool"),
                            localDate,
                            data.getString("comment"),
                            category
                    );
                }
            }
        } catch (Exception e) {
            throw new ReadException("Error while searching for the beer : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateBeer(Beer beer) throws Exception {
        String sql = "UPDATE Beer SET name = ?, color = ?, price = ?, description = ?, containsAlcool = ?, " +
                "marketLaunchDate = ?, comment = ?, categoryId = ? WHERE beerId = ?";
        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, beer.getName());
            statement.setString(2, beer.getColor());
            statement.setDouble(3, beer.getPrice());
            statement.setString(4, beer.getDescription());
            statement.setBoolean(5, beer.getContainsAlcool());
            statement.setDate(6, beer.getMarketLaunchDate() != null ? Date.valueOf(beer.getMarketLaunchDate()) : null);
            statement.setString(7, beer.getComment());

            // MODIFICATION : Extraction de l'ID depuis l'objet
            if (beer.getCategory() != null) {
                statement.setInt(8, beer.getCategory().getCategoryId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            statement.setInt(9, beer.getBeerId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error while updating the beer : " + e.getMessage());
        }
    }

    @Override
    public void deleteBeer(int beerId) throws Exception {
        String sql = "DELETE FROM Beer WHERE beerId = ?";
        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, beerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error while deleting the beer. Note: Make sure it is not linked to an ongoing order. Details : " + e.getMessage());
        }
    }
}