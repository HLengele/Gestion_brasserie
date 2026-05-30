package dataAccess;

import exception.ReadException;
import model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDBAccess implements CategoryDataAccess {

    @Override
    public ArrayList<Category> readAll() throws ReadException {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY name";

        // Using try-with-resources to automatically close statement and data
        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet data = statement.executeQuery()) {

            while (data.next()) {
                // The constructor may throw a NullValueException
                categories.add(new Category(
                        data.getInt("id"),
                        data.getString("name")
                ));
            }
            return categories;
        } catch (Exception e) {
            // Catches everything (SQLException or NullValueException)
            throw new ReadException("Error while reading categories: " + e.getMessage());
        }
    }

    @Override
    public Category readById(int id) throws ReadException {
        String sql = "SELECT * FROM Category WHERE id = ?";

        try (Connection connection = SingletonConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet data = statement.executeQuery()) {
                if (data.next()) {
                    return new Category(
                            data.getInt("id"),
                            data.getString("name")
                    );
                }
            }
            return null; // Returns null if no category found with this ID
        } catch (Exception e) {
            throw new ReadException("Error while searching for category ID " + id + ": " + e.getMessage());
        }
    }
}