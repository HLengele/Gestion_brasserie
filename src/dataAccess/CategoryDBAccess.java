package dataAccess;

import exception.ReadException;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDBAccess implements CategoryDataAccess {

    public ArrayList<Category> readAll() throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();

            ArrayList<Category> categories = new ArrayList<>();
            Category category = null;

            String sql = "SELECT * FROM Category ORDER BY name";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet data = statement.executeQuery();

            while(data.next()) {
                category = new Category(
                        data.getInt("id"),
                        data.getString("name")
                );
                categories.add(category);
            }
            return categories;
        } catch (SQLException sqlException) {
            throw new ReadException(sqlException.getMessage());
        }
    }

    public Category readById(int id) throws ReadException {
        try {
            Connection connection = SingletonConnection.getInstance();

            Category category = null;

            String sql = "SELECT * FROM Category WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet data = statement.executeQuery();

            while(data.next()) {
                category = new Category(
                        data.getInt("id"),
                        data.getString("name")
                );
            }
            return category;
        } catch (SQLException exception) {
            throw new ReadException(exception.getMessage());
        }
    }
}