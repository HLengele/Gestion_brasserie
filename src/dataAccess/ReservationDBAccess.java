package dataAccess;

import model.Customer;
import model.Reservation;
import model.Table;
import exception.ReadException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDBAccess implements ReservationDataAccess {

    public ArrayList<Reservation> getReservationsBetweenDates(LocalDate start, LocalDate end) throws ReadException {
        ArrayList<Reservation> list = new ArrayList<>();

        String sql = "SELECT r.reservationId, r.date, r.nbPeople, " +
                "       c.customerId, c.name AS customerName, c.phone, c.email, " +
                "       t.tableNumber, t.nbPlace, t.location " +
                "FROM Reservation r " +
                "JOIN Customer c ON r.customerId = c.customerId " +
                "JOIN `table` t ON r.tableNumber = t.tableNumber " +
                "WHERE DATE(r.date) BETWEEN ? AND ?";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(start));
            statement.setDate(2, java.sql.Date.valueOf(end));

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    Customer customer = new Customer(
                            rs.getInt("customerId"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("customerName")
                    );

                    Table table = new Table(
                            rs.getInt("tableNumber"),
                            rs.getInt("nbPlace"),
                            rs.getString("location")
                    );

                    // Instanciation de la Réservation
                    Reservation reservation = new Reservation(
                            rs.getInt("reservationId"),
                            rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
                            rs.getInt("nbPeople"),
                            customer,
                            table
                    );

                    list.add(reservation);
                }
            }
        } catch (Exception e) {
            throw new ReadException("Erreur lors de la recherche des réservations : " + e.getMessage());
        }
        return list;
    }
}