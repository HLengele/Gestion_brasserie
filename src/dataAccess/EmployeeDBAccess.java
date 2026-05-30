package dataAccess;

import exception.ReadException;
import model.Employee;
import model.City;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class EmployeeDBAccess implements EmployeeDataAccess {

    @Override
    public ArrayList<Employee> readAll() throws ReadException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee ORDER BY employeeId";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet data = statement.executeQuery()) {

            while (data.next()) {
                Date sqlDate = data.getDate("hiringDate");
                LocalDate localHiringDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                City city = null;
                employees.add(new Employee(
                        data.getInt("employeeId"),
                        data.getString("firstName"),
                        data.getString("lastName"),
                        localHiringDate,
                        city
                ));
            }
            return employees;
        } catch (Exception exception) {
            throw new ReadException("Error while reading employees: " + exception.getMessage());
        }
    }

    @Override
    public Employee readById(int id) throws ReadException {
        String sql = "SELECT * FROM Employee WHERE employeeId = ?";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet data = statement.executeQuery()) {
                if (data.next()) {
                    Date sqlDate = data.getDate("hiringDate");
                    LocalDate localHiringDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    City city = null;
                    return new Employee(
                            data.getInt("employeeId"),
                            data.getString("firstName"),
                            data.getString("lastName"),
                            localHiringDate,
                            city
                    );
                }
            }
            return null;
        } catch (Exception exception) {
            throw new ReadException("Error while searching for employee ID " + id + ": " + exception.getMessage());
        }
    }
}