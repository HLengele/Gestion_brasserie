package dataAccess;

import exception.ReadException;
import model.Employee;
import model.City; // Ne pas oublier l'import
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class EmployeeDBAccess implements EmployeeDataAccess {

    @Override
    public ArrayList<Employee> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();
            ArrayList<Employee> employees = new ArrayList<>();

            // On sélectionne aussi la clé étrangère (ex: cityId) si elle est dans la table
            String sql = "SELECT * FROM Employee ORDER BY employeeId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while(data.next()) {
                // 1. Conversion de la date
                Date sqlDate = data.getDate("hiringDate");
                LocalDate localHiringDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                // 2. Récupération de la ville (Idéalement via votre CityDBAccess)
                // int cityId = data.getInt("cityId");
                // City city = cityDBAccess.readById(cityId);
                City city = null; // À remplacer par un vrai objet City si vous l'avez déjà implémenté

                // 3. Récupération de la capacité (si elle est en BDD, sinon mettez une valeur par défaut)
                int capacite = data.getInt("capacite");

                Employee employee = new Employee(
                        data.getInt("employeeId"),
                        data.getString("firstName"), // Remis dans le bon ordre
                        data.getString("lastName"),
                        localHiringDate,
                        city,
                        capacite
                );
                employees.add(employee);
            }
            return employees;
        } catch (Exception exception) { // Changé en Exception pour capturer NullValueException
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
                Date sqlDate = data.getDate("hiringDate");
                LocalDate localHiringDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                // Idem ici pour city et capacite
                City city = null;
                int capacite = data.getInt("capacite");

                employee = new Employee(
                        data.getInt("employeeId"),
                        data.getString("firstName"),
                        data.getString("lastName"),
                        localHiringDate,
                        city,
                        capacite
                );
            }
            return employee;
        } catch (Exception exception) {
            throw new ReadException(exception.getMessage());
        }
    }
}