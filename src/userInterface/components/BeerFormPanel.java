package userInterface.components;

import exception.NullValueException;
import model.Beer;
import model.Category;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BeerFormPanel extends JPanel {

    private JTextField          txtId;
    private JTextField          txtName;
    private JTextField          txtColor;
    private JTextField          txtPrice;
    private JCheckBox           chkAlcohol;
    private JTextField          txtLaunchDate;
    private JTextField          txtDescription;
    private JTextField          txtComment;
    private JComboBox<Category> comboCategory;   // CORRECTION : Category au lieu de String

    private AddBeerButton    addButton;
    private UpdateBeerButton updateButton;
    private DeleteBeerButton deleteButton;
    private ClearBeerButton  clearButton;

    private JPanel    groupButtons;
    private BeerTable beerTable;

    public BeerFormPanel(MainWindow parent) {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel hintLabel = new JLabel("Fields marked with * are mandatory.");
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setFont(hintLabel.getFont().deriveFont(Font.ITALIC, 11f));
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(hintLabel);
        this.add(Box.createVerticalStrut(10));

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
        comboCategory.addItem(null);
        comboCategory.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "-- Select a category --" : ((Category) value).getName());
                return this;
            }
        });

        addRow("ID :",                         txtId,         false);
        addRow("Name * :",                     txtName,       true);
        addRow("Color * :",                    txtColor,      true);
        addRow("Price (€) * :",                txtPrice,      true);
        addRow("Contains alcohol * :",         chkAlcohol,    true);
        addRow("Launch date * (YYYY-MM-DD) :", txtLaunchDate, true);
        addRow("Description :",                txtDescription, false);
        addRow("Comment :",                    txtComment,    false);
        addRow("Category :",                   comboCategory, false);

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

    private void addRow(String labelText, JComponent field, boolean mandatory) {
        JLabel label = new JLabel(labelText);
        if (mandatory) {
            label.setForeground(new Color(160, 40, 40));
        }
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
        comboCategory.addItem(null);
        if (categories != null) {
            for (Category cat : categories) {
                comboCategory.addItem(cat);
            }
        }
    }

    public Beer formToBeer() throws NullValueException {

        List<String> errors = new ArrayList<>();

        Integer id = null;
        String idTxt = txtId.getText().trim();
        if (!idTxt.isEmpty()) {
            try {
                id = Integer.parseInt(idTxt);
            } catch (NumberFormatException e) {
                errors.add("- ID : invalid format.");
            }
        }

        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            errors.add("- Name : this field is required.");
        }

        String color = txtColor.getText().trim();
        if (color.isEmpty()) {
            errors.add("- Color : this field is required.");
        }

        Double price = null;
        String priceTxt = txtPrice.getText().trim().replace(",", ".");
        if (priceTxt.isEmpty()) {
            errors.add("- Price : this field is required.");
        } else {
            try {
                price = Double.parseDouble(priceTxt);
                if (price < 0) {
                    errors.add("- Price : must be a positive value (≥ 0).");
                }
            } catch (NumberFormatException e) {
                errors.add("- Price : invalid format. Please enter a number (e.g. 4.50).");
            }
        }


        LocalDate launchDate = null;
        String dateTxt = txtLaunchDate.getText().trim();
        if (dateTxt.isEmpty()) {
            errors.add("- Launch date : this field is required (format YYYY-MM-DD).");
        } else {
            try {
                launchDate = LocalDate.parse(dateTxt);
                if (launchDate.isAfter(LocalDate.now())) {
                    errors.add("- Launch date : cannot be set in the future.");
                }
            } catch (DateTimeParseException e) {
                errors.add("- Launch date : invalid format. Use YYYY-MM-DD (e.g. 2023-05-14).");
            }
        }
        Boolean containsAlcool = chkAlcohol.isSelected();
        String description = txtDescription.getText().trim().isEmpty()
                ? null
                : txtDescription.getText().trim();

        String comment = txtComment.getText().trim().isEmpty()
                ? null
                : txtComment.getText().trim();

        Category category = (Category) comboCategory.getSelectedItem();

        if (!errors.isEmpty()) {
            String msg = "Please correct the following mandatory fields:\n\n"
                    + String.join("\n", errors);
            throw new NullValueException(msg);
        }

        return new Beer(id, name, color, price, description, containsAlcool, launchDate, comment, category);
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
            comboCategory.setSelectedIndex(0); // retour au placeholder
            return;
        }

        txtId.setText(beer.getBeerId()   != null ? String.valueOf(beer.getBeerId()) : "");
        txtName.setText(beer.getName()   != null ? beer.getName()   : "");
        txtColor.setText(beer.getColor() != null ? beer.getColor()  : "");
        txtPrice.setText(beer.getPrice() != null ? String.valueOf(beer.getPrice()) : "");
        chkAlcohol.setSelected(beer.getContainsAlcool() != null && beer.getContainsAlcool());
        txtLaunchDate.setText(beer.getMarketLaunchDate() != null
                ? beer.getMarketLaunchDate().toString() : "");
        txtDescription.setText(beer.getDescription() != null ? beer.getDescription() : "");
        txtComment.setText(beer.getComment()         != null ? beer.getComment()     : "");

        comboCategory.setSelectedIndex(0); // placeholder par défaut
        Category cat = beer.getCategory();
        if (cat != null) {
            for (int i = 0; i < comboCategory.getItemCount(); i++) {
                Category item = comboCategory.getItemAt(i);
                if (item != null && item.getCategoryId() == cat.getCategoryId()) {
                    comboCategory.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public Integer getCurrentBeerId() {
        String txt = txtId.getText().trim();
        return txt.isEmpty() ? null : Integer.parseInt(txt);
    }

    public void setCurrentBeerId(Integer id) {
        txtId.setText(id == null ? "" : String.valueOf(id));
    }

    public BeerTable getBeerTable()                { return beerTable; }
    public void setBeerTable(BeerTable beerTable)  { this.beerTable = beerTable; }
}