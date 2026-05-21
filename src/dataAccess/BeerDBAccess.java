package dataAccess;

import exception.ReadException;
import model.Beer;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class BeerDBAccess implements BeerDataAccess {

    @Override
    public void insertBeer(Beer beer) throws Exception {
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

            if (beer.getCategoryId() != null) statement.setInt(8, beer.getCategoryId());
            else statement.setNull(8, Types.INTEGER);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'ajout de la bière : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Beer> readAll() throws ReadException {
        ArrayList<Beer> beers = new ArrayList<>();
        String sql = "SELECT * FROM Beer ORDER BY name";
        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet data = statement.executeQuery()) {

            while (data.next()) {
                Date sqlDate = data.getDate("marketLaunchDate");
                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                Integer catId = data.getInt("categoryId");
                if (data.wasNull()) catId = null;

                Beer beer = new Beer(
                        data.getInt("beerId"),
                        data.getString("name"),
                        data.getString("color"),
                        data.getDouble("price"),
                        data.getString("description"),
                        data.getBoolean("containsAlcool"),
                        localDate,
                        data.getString("comment"),
                        catId
                );
                beers.add(beer);
            }
        } catch (Exception e) {
            throw new ReadException("Erreur de lecture des bières : " + e.getMessage());
        }
        return beers;
    }

    @Override
    public Beer readById(int id) throws ReadException {
        String sql = "SELECT * FROM Beer WHERE beerId = ?";
        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet data = statement.executeQuery()) {
                if (data.next()) {
                    Date sqlDate = data.getDate("marketLaunchDate");
                    LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    Integer catId = data.getInt("categoryId");
                    if (data.wasNull()) catId = null;

                    return new Beer(
                            data.getInt("beerId"),
                            data.getString("name"),
                            data.getString("color"),
                            data.getDouble("price"),
                            data.getString("description"),
                            data.getBoolean("containsAlcool"),
                            localDate,
                            data.getString("comment"),
                            catId
                    );
                }
            }
        } catch (Exception e) {
            throw new ReadException("Erreur lors de la recherche de la bière : " + e.getMessage());
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

            if (beer.getCategoryId() != null) statement.setInt(8, beer.getCategoryId());
            else statement.setNull(8, Types.INTEGER);

            statement.setInt(9, beer.getBeerId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la modification de la bière : " + e.getMessage());
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
            throw new Exception("Erreur lors de la suppression de la bière. Note : Vérifiez qu'elle n'est pas liée à une commande en cours. Détails : " + e.getMessage());
        }
    }
}