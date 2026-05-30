package dataAccess;

import model.City;
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

        // MODIFICATION: Added backticks around the reserved word `table`
        String sql = "SELECT r.reservationId, r.date, r.nbPeople, " +
                "       c.customerId, c.name AS customerName, c.phone, c.email, " +
                "       ci.id AS cityId, ci.name AS cityName, ci.postalCode, " +
                "       t.tableNumber, t.nbPlace, t.location " +
                "FROM Reservation r " +
                "JOIN Customer c ON r.customerId = c.customerId " +
                "LEFT JOIN City ci ON c.cityId = ci.id " +
                "JOIN `table` t ON r.tableNumber = t.tableNumber " +
                "WHERE DATE(r.date) BETWEEN ? AND ?";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(start));
            statement.setDate(2, java.sql.Date.valueOf(end));

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    City city = null;
                    if (rs.getInt("cityId") != 0) {
                        city = new City(
                                rs.getInt("cityId"),
                                rs.getString("cityName"),
                                rs.getString("postalCode")
                        );
                    }

                    Customer customer = new Customer(
                            rs.getInt("customerId"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("customerName"),
                            city
                    );

                    Table table = new Table(
                            rs.getInt("tableNumber"),
                            rs.getInt("nbPlace"),
                            rs.getString("location")
                    );

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
            throw new ReadException("Error while searching for reservations: " + e.getMessage());
        }
        return list;
    }
}