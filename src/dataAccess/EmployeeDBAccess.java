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
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<Employee> employees = new ArrayList<>();

            String sql = "SELECT * FROM Employee ORDER BY employeeId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while(data.next()) {
                // 1. Conversion de la date d'embauche
                Date sqlDate = data.getDate("hiringDate");
                LocalDate localHiringDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                // 2. Récupération de la ville (Initialisé à null)
                // Si vous liez plus tard avec CityDBAccess, vous pourrez faire :
                // int cityId = data.getInt("cityId");
                // City city = new CityDBAccess().readById(cityId);
                City city = null;

                // 3. Instanciation de l'employé (sans le paramètre capacite)
                Employee employee = new Employee(
                        data.getInt("employeeId"),
                        data.getString("firstName"),
                        data.getString("lastName"),
                        localHiringDate,
                        city
                );
                employees.add(employee);
            }
            return employees;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }

    @Override
    public Employee readById(int id) throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            Employee employee = null;

            String sql = "SELECT * FROM Employee WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet data = statement.executeQuery();

            if (data.next()) {
                // 1. Conversion de la date d'embauche
                Date sqlDate = data.getDate("hiringDate");
                LocalDate localHiringDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                // 2. Récupération de la ville (Initialisé à null)
                City city = null;

                // 3. Instanciation de l'employé (sans le paramètre capacite ni la lecture de colonne obsolète)
                employee = new Employee(
                        data.getInt("employeeId"),
                        data.getString("firstName"),
                        data.getString("lastName"),
                        localHiringDate,
                        city
                );
            }
            return employee;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }
}