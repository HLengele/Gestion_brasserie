package userInterface.components;

import model.Beer;
import model.Category;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class BeerFormPanel extends JPanel {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtColor;
    private JTextField txtPrice;
    private JCheckBox  chkAlcohol;
    private JTextField txtLaunchDate;
    private JTextField txtDescription;
    private JTextField txtComment;
    private JComboBox<String> comboCategory;

    private AddBeerButton    addButton;
    private UpdateBeerButton updateButton;
    private DeleteBeerButton deleteButton;
    private ClearBeerButton  clearButton;

    private JPanel groupButtons;

    private BeerTable beerTable;

    public BeerFormPanel(MainWindow parent) {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);

        txtName        = new JTextField();
        txtColor       = new JTextField();
        txtPrice       = new JTextField();
        chkAlcohol     = new JCheckBox("Yes");
        txtLaunchDate  = new JTextField();
        txtDescription = new JTextField();
        txtComment     = new JTextField();

        comboCategory = new JComboBox<>();
        comboCategory.addItem("-- Select a category --");

        // --- Add label + field for each property ---
        addRow("ID :", txtId);
        addRow("Name :", txtName);
        addRow("Color :", txtColor);
        addRow("Price (€) :", txtPrice);
        addRow("Contains alcohol :", chkAlcohol);
        addRow("Launch Date (YYYY-MM-DD) :", txtLaunchDate);
        addRow("Description :", txtDescription);
        addRow("Comment :", txtComment);
        addRow("Category :", comboCategory);

        addButton    = new AddBeerButton(this, parent);
        updateButton = new UpdateBeerButton(this, parent);
        deleteButton = new DeleteBeerButton(this, parent);
        clearButton  = new ClearBeerButton(this, parent);

        groupButtons = new JPanel();
        groupButtons.setLayout(new BoxLayout(groupButtons, BoxLayout.X_AXIS));
        groupButtons.setBorder(new EmptyBorder(10, 0, 0, 0));
        groupButtons.setOpaque(false);
        groupButtons.add(addButton);
        groupButtons.add(Box.createHorizontalStrut(5));
        groupButtons.add(updateButton);
        groupButtons.add(Box.createHorizontalStrut(5));
        groupButtons.add(deleteButton);
        groupButtons.add(Box.createHorizontalStrut(5));
        groupButtons.add(clearButton);

        this.add(groupButtons);
    }

    private void addRow(String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (field instanceof JTextField) {
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        }
        this.add(label);
        this.add(field);
        this.add(Box.createVerticalStrut(6));
    }

    public void populateCategories(ArrayList<Category> categories) {
        comboCategory.removeAllItems();
        comboCategory.addItem("-- Select a category --");
        if (categories != null) {
            for (Category cat : categories) {
                comboCategory.addItem(cat.getName() + " (" + cat.getCategoryId() + ")");
            }
        }
    }

    public Beer formToBeer() throws Exception {
        Integer id = null;
        if (!txtId.getText().trim().isEmpty()) {
            id = Integer.parseInt(txtId.getText().trim());
        }

        String name        = txtName.getText().trim();
        String color       = txtColor.getText().trim();
        String description = txtDescription.getText().trim().isEmpty() ? null : txtDescription.getText().trim();
        String comment     = txtComment.getText().trim().isEmpty()     ? null : txtComment.getText().trim();

        Double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new Exception("The price format is invalid.");
        }

        Boolean containsAlcool = chkAlcohol.isSelected();

        LocalDate launchDate = null;
        String dateTxt = txtLaunchDate.getText().trim();
        if (!dateTxt.isEmpty()) {
            try {
                launchDate = LocalDate.parse(dateTxt);
            } catch (DateTimeParseException ex) {
                throw new Exception("The date format is incorrect. Use YYYY-MM-DD.");
            }
        }

        Integer categoryId = null;
        String selectedCat = (String) comboCategory.getSelectedItem();
        if (selectedCat != null && !selectedCat.startsWith("--")) {
            int openParen  = selectedCat.lastIndexOf("(");
            int closeParen = selectedCat.lastIndexOf(")");
            if (openParen != -1 && closeParen != -1) {
                categoryId = Integer.parseInt(selectedCat.substring(openParen + 1, closeParen));
            }
        }

        return new Beer(id, name, color, price, description, containsAlcool, launchDate, comment, categoryId);
    }

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

        txtId.setText(beer.getBeerId()   != null ? String.valueOf(beer.getBeerId()) : "");
        txtName.setText(beer.getName()   != null ? beer.getName()   : "");
        txtColor.setText(beer.getColor() != null ? beer.getColor()  : "");
        txtPrice.setText(beer.getPrice() != null ? String.valueOf(beer.getPrice()) : "");
        chkAlcohol.setSelected(beer.getContainsAlcool() != null && beer.getContainsAlcool());
        txtLaunchDate.setText(beer.getMarketLaunchDate() != null ? beer.getMarketLaunchDate().toString() : "");
        txtDescription.setText(beer.getDescription() != null ? beer.getDescription() : "");
        txtComment.setText(beer.getComment()          != null ? beer.getComment()    : "");

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

    public Integer getCurrentBeerId() {
        String txt = txtId.getText().trim();
        return txt.isEmpty() ? null : Integer.parseInt(txt);
    }

    public void setCurrentBeerId(Integer id) {
        txtId.setText(id == null ? "" : String.valueOf(id));
    }

    public BeerTable getBeerTable() { return beerTable; }
    public void setBeerTable(BeerTable beerTable) { this.beerTable = beerTable; }
}
