package dataAccess;

import exception.ReadException;
import model.Beer;
import java.sql.*;
import java.util.ArrayList;

public class BeerDBAccess implements BeerDataAccess {

    // ── MÉTHODE 1 : Lire toutes les bières ──────────────────────────────────────
    @Override
    public ArrayList<Beer> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<Beer> beers = new ArrayList<>();
            Beer beer = null;

            String sql = "SELECT * FROM Beer ORDER BY beerId";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while (data.next()) {
                beer = new Beer(
                        data.getInt("beerId"),
                        data.getString("name"),  // Utilisez "nom" si votre colonne s'appelle ainsi en BD
                        data.getDouble("price"),
                        data.getString("type")
                );
                beers.add(beer);
            }

            return beers;

        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }

    // ── MÉTHODE 2 : Trouver une bière par son ID (Ajoutée pour corriger l'erreur !) ──
    @Override
    public Beer readById(int id) throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            Beer beer = null;

            // Utilisation d'un paramètre '?' pour éviter les injections SQL (Consigne du cours)
            String sql = "SELECT * FROM Beer WHERE beerId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            // On remplace le premier '?' par la valeur de la variable 'id'
            statement.setInt(1, id);

            ResultSet data = statement.executeQuery();

            // S'il y a un résultat, on instancie notre objet Beer
            if (data.next()) {
                beer = new Beer(
                        data.getInt("beerId"),
                        data.getString("name"),
                        data.getDouble("price"),
                        data.getString("type")
                );
            }

            return beer;

        } catch (Exception exception) {
            // Respect de la structure du cours : conversion en ReadException
            throw new ReadException(exception.getMessage());
        }
    }
}