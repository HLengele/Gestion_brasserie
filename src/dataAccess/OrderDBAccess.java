package dataAccess;

import exception.*;
import model.Order;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDBAccess implements OrderDataAccess {

    @Override
    public int insertOrder(Order newOrder) throws AddOrderException {
        String sql = "INSERT INTO `Order` (hour, tableNumber) VALUES (?, ?)";

        try {
            Connection connection = SingletonConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setTime(1, Time.valueOf(newOrder.getHour()));
                statement.setInt(2, newOrder.getTableNumber());
                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
                throw new AddOrderException("Échec de l'insertion : aucun ID n'a été généré.");
            }
        } catch (SQLException exception) {
            throw new AddOrderException("Erreur SQL lors de l'ajout de la commande : " + exception.getMessage());
        }
    }

    @Override
    public void insertLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
        String sql = "INSERT INTO Line_Order (quantity, realPrice, orderId, beerId) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = SingletonConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, quantity);
                statement.setDouble(2, realPrice);
                statement.setInt(3, orderId);
                statement.setInt(4, beerId);

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new Exception("Erreur lors de l'insertion de la ligne de commande : " + exception.getMessage());
        }
    }


    @Override
    public double getTotalPriceByTable(int tableNumber) throws ReadException {
        double total = 0.0;

        // Utilisation d'un LEFT JOIN : si aucune ligne de commande, le SUM renverra 0 proprement au lieu de tout bloquer
        String query = "SELECT SUM(lo.realPrice * lo.quantity) " +
                "FROM `Order` o " +
                "LEFT JOIN Line_Order lo ON o.orderId = lo.orderId " +
                "WHERE o.tableNumber = ?;";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, tableNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Utiliser rs.getDouble(1) prend la première colonne calculée, c'est 100% fiable
                    total = rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            throw new ReadException("Erreur lors du calcul de l'addition : " + e.getMessage());
        }

        return total;
    }
}