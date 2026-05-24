package dataAccess;

import exception.ReadException;
import model.ReservationSearchResult;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReservationDBAccess implements ReservationDataAccess {

    @Override
    public ArrayList<ReservationSearchResult> getReservationsBetweenDates(LocalDate startDate, LocalDate endDate) throws ReadException {
        ArrayList<ReservationSearchResult> results = new ArrayList<>();

        // Jointure sur Reservation (r), Customer (c), City (ci)
        String sql = "SELECT r.date, r.nbPeople, c.name AS customerName, ci.name AS cityName, ci.postalCode, r.tableNumber " +
                "FROM Reservation r " +
                "JOIN Customer c ON r.customerId = c.customerId " +
                "JOIN City ci ON c.cityId = ci.id " +
                "WHERE DATE(r.date) BETWEEN ? AND ? " +
                "ORDER BY r.date ASC";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            try (ResultSet data = statement.executeQuery()) {
                while (data.next()) {
                    Timestamp sqlTimestamp = data.getTimestamp("date");
                    LocalDateTime localDateTime = (sqlTimestamp != null) ? sqlTimestamp.toLocalDateTime() : null;

                    ReservationSearchResult result = new ReservationSearchResult(
                            localDateTime,
                            data.getInt("nbPeople"),
                            data.getString("customerName"),
                            data.getString("cityName"),
                            data.getString("postalCode"),
                            data.getInt("tableNumber")
                    );
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            throw new ReadException("Error while searching for reservations : " + e.getMessage());
        }

        return results;
    }
}