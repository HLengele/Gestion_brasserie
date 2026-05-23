package userInterface.panel;

import model.Beer;
import model.Category;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class BeerFormPanel extends JPanel {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtColor;
    private JTextField txtPrice;
    private JCheckBox chkAlcohol;
    private JTextField txtLaunchDate;
    private JTextField txtDescription;
    private JTextField txtComment;
    private JComboBox<String> comboCategory;

    public BeerFormPanel() {
        this.setLayout(new GridLayout(9, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(new JLabel("ID :"));
        txtId = new JTextField();
        txtId.setEditable(false);
        this.add(txtId);

        this.add(new JLabel("Nom :"));
        txtName = new JTextField();
        this.add(txtName);

        this.add(new JLabel("Couleur :"));
        txtColor = new JTextField();
        this.add(txtColor);

        this.add(new JLabel("Prix (€) :"));
        txtPrice = new JTextField();
        this.add(txtPrice);

        this.add(new JLabel("Contient de l'alcool :"));
        chkAlcohol = new JCheckBox("Oui");
        this.add(chkAlcohol);

        this.add(new JLabel("Date Lancement (YYYY-MM-DD) :"));
        txtLaunchDate = new JTextField();
        this.add(txtLaunchDate);

        this.add(new JLabel("Description :"));
        txtDescription = new JTextField();
        this.add(txtDescription);

        this.add(new JLabel("Commentaire :"));
        txtComment = new JTextField();
        this.add(txtComment);

        this.add(new JLabel("Catégorie :"));
        comboCategory = new JComboBox<>();
        comboCategory.addItem("-- Sélectionner une catégorie --");
        this.add(comboCategory);
    }

    public void populateCategories(ArrayList<Category> categories) {
        comboCategory.removeAllItems();
        comboCategory.addItem("-- Sélectionner une catégorie --");
        if (categories != null) {
            for (Category cat : categories) {
                comboCategory.addItem(cat.getName() + " (" + cat.getCategoryId() + ")");
            }
        }
    }

    /**
     * Extrait les données du formulaire en respectant l'ordre exact du modèle Beer.
     */
    public Beer formToBeer() throws Exception {
        Integer id = null;
        if (!txtId.getText().trim().isEmpty()) {
            id = Integer.parseInt(txtId.getText().trim());
        }

        String name = txtName.getText().trim();
        String color = txtColor.getText().trim();
        String description = txtDescription.getText().trim().isEmpty() ? null : txtDescription.getText().trim();
        String comment = txtComment.getText().trim().isEmpty() ? null : txtComment.getText().trim();

        Double price = null;
        try {
            price = Double.parseDouble(txtPrice.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new Exception("Le format du prix est invalide.");
        }

        Boolean containsAlcool = chkAlcohol.isSelected();

        LocalDate launchDate = null;
        String dateTxt = txtLaunchDate.getText().trim();
        if (!dateTxt.isEmpty()) {
            try {
                launchDate = LocalDate.parse(dateTxt);
            } catch (DateTimeParseException ex) {
                throw new Exception("Le format de la date est incorrect. Utilisez YYYY-MM-DD.");
            }
        }

        Integer categoryId = null;
        String selectedCat = (String) comboCategory.getSelectedItem();
        if (selectedCat != null && !selectedCat.startsWith("--")) {
            int openParen = selectedCat.lastIndexOf("(");
            int closeParen = selectedCat.lastIndexOf(")");
            if (openParen != -1 && closeParen != -1) {
                categoryId = Integer.parseInt(selectedCat.substring(openParen + 1, closeParen));
            }
        }

        // CORRECTION DE L'INVERSION ICI : 'name' passe avant 'color'
        Beer beer = new Beer(id, name, color, price, description, containsAlcool, launchDate, comment, categoryId);

        return beer;
    }

    /**
     * Remplit le formulaire lors d'un clic sur le tableau
     */
    public void beerToForm(Beer beer) {
        if (beer == null) {
            txtId.setText("");
            txtName.setText("");
            txtColor.setText("");
            txtPrice.setText("");
            chkAlcohol.setSelected(false);
            txtLaunchDate.setText("");
            txtDescription.setText("");
            txtComment.setText("");
            comboCategory.setSelectedIndex(0);
            return;
        }

        txtId.setText(beer.getBeerId() != null ? String.valueOf(beer.getBeerId()) : "");
        txtName.setText(beer.getName() != null ? beer.getName() : "");
        txtColor.setText(beer.getColor() != null ? beer.getColor() : "");
        txtPrice.setText(beer.getPrice() != null ? String.valueOf(beer.getPrice()) : "");
        chkAlcohol.setSelected(beer.getContainsAlcool() != null && beer.getContainsAlcool());
        txtLaunchDate.setText(beer.getMarketLaunchDate() != null ? beer.getMarketLaunchDate().toString() : "");
        txtDescription.setText(beer.getDescription() != null ? beer.getDescription() : "");
        txtComment.setText(beer.getComment() != null ? beer.getComment() : "");

        Integer targetCatId = beer.getCategoryId();
        if (targetCatId != null && targetCatId != 0) {
            for (int i = 0; i < comboCategory.getItemCount(); i++) {
                String item = comboCategory.getItemAt(i);
                if (item.endsWith("(" + targetCatId + ")")) {
                    comboCategory.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            comboCategory.setSelectedIndex(0);
        }
    }

    public void setCurrentBeerId(Integer id) {
        if (id == null) {
            txtId.setText("");
        } else {
            txtId.setText(String.valueOf(id));
        }
    }
}