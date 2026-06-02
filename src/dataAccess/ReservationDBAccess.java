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

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {

                    City city = null;
                    if (result.getInt("cityId") != 0) {
                        city = new City(
                                result.getInt("cityId"),
                                result.getString("cityName"),
                                result.getString("postalCode")
                        );
                    }

                    Customer customer = new Customer(
                            result.getInt("customerId"),
                            result.getString("email"),
                            result.getString("phone"),
                            result.getString("customerName"),
                            city
                    );

                    Table table = new Table(
                            result.getInt("tableNumber"),
                            result.getInt("nbPlace"),
                            result.getString("location")
                    );

                    Reservation reservation = new Reservation(
                            result.getInt("reservationId"),
                            result.getTimestamp("date").toLocalDateTime().toLocalDate(),
                            result.getInt("nbPeople"),
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