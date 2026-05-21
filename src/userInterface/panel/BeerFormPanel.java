package userInterface.panel; // Ou le nom de votre package graphique (ex: gui, userInterface)

import model.Beer;
import javax.swing.*;

public class BeerFormPanel extends JPanel { // Ou extends JFrame selon votre structure

    // 1. Vos composants graphiques existants (JTextField, JLabel, etc.)
    private JTextField txtName, txtColor, txtPrice, txtDescription, txtComment, txtMarketLaunchDate, txtCategoryId;
    private JButton btnSave, btnCancel;

    // 2. PLACEZ LA NOUVELLE VARIABLE ICI (au niveau des attributs de la classe)
    private Integer currentBeerId;

    // 3. Votre constructeur (où vous initialisez et placez vos composants)
    public BeerFormPanel() {
        // Code d'initialisation de l'interface (setLayout, add(txtName), etc.)
        // ...
    }
    public Integer getCurrentBeerId() {
        return currentBeerId;
    }

    public void setCurrentBeerId(Integer id) {
        this.currentBeerId = id;
    }

    // 4. INSÉREZ LA MÉTHODE ICI (à la place de l'ancienne méthode orderToForm)
    public void beerToForm(Beer beer) {
        this.currentBeerId = beer.getBeerId();

        this.txtName.setText(beer.getName());
        this.txtColor.setText(beer.getColor());
        this.txtPrice.setText(String.valueOf(beer.getPrice()));
        this.txtDescription.setText(beer.getDescription());
        this.txtComment.setText(beer.getComment());

        if (beer.getMarketLaunchDate() != null) {
            this.txtMarketLaunchDate.setText(beer.getMarketLaunchDate().toString());
        } else {
            this.txtMarketLaunchDate.setText("");
        }

        if (beer.getCategoryId() != null) {
            this.txtCategoryId.setText(String.valueOf(beer.getCategoryId()));
        } else {
            this.txtCategoryId.setText("");
        }
    }

    // 5. AJOUTEZ ÉGALEMENT LA MÉTHODE INVERSE ICI
    public Beer formToBeer() throws Exception {
        String name = this.txtName.getText().trim();
        String color = this.txtColor.getText().trim();

        double price;
        try {
            price = Double.parseDouble(this.txtPrice.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("Le prix doit être un nombre valide.");
        }

        String description = this.txtDescription.getText().trim();
        String comment = this.txtComment.getText().trim();

        java.time.LocalDate launchDate = null;
        if (!this.txtMarketLaunchDate.getText().isBlank()) {
            launchDate = java.time.LocalDate.parse(this.txtMarketLaunchDate.getText().trim());
        }

        Integer categoryId = null;
        if (!this.txtCategoryId.getText().isBlank()) {
            categoryId = Integer.parseInt(this.txtCategoryId.getText().trim());
        }

        // On réutilise la variable currentBeerId stockée lors du chargement
        return new Beer(this.currentBeerId, name, color, price, description, true, launchDate, comment, categoryId);
    }
}