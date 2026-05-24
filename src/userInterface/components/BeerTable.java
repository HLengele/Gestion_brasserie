package userInterface.components;

import exception.ReadException;
import model.Beer;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Tableau JTable affichant la liste des bières.
 * Expose loadTable() pour le rafraîchissement après chaque opération CRUD,
 * appelé par les boutons dédiés (pattern UI2 : orderForm.getOrderTable().loadTable()).
 */
public class BeerTable extends JTable {

    private DefaultTableModel tableModel;
    private MainWindow parent;
    private BeerFormPanel linkedForm;

    public BeerTable(MainWindow parent) {
        this.parent = parent;

        String[] columns = {"ID", "Name", "Color", "Price", "Alcohol", "Launch", "Description", "Comment", "Category"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        this.setModel(tableModel);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setPreferredWidth(110);
        }
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadTable();
    }

    /**
     * (Re)charge toutes les bières depuis la base de données.
     * Appelé par les boutons CRUD après chaque opération.
     */
    public void loadTable() {
        tableModel.setRowCount(0);
        try {
            ArrayList<Beer> beers = parent.getApplicationController().getAllBeers();
            for (Beer b : beers) {
                tableModel.addRow(new Object[]{
                        b.getBeerId(),
                        b.getName(),
                        b.getColor(),
                        String.format("%.2f €", b.getPrice() != null ? b.getPrice() : 0.0),
                        (b.getContainsAlcool() != null && b.getContainsAlcool()) ? "Yes" : "No",
                        b.getMarketLaunchDate() != null ? b.getMarketLaunchDate() : "N/A",
                        b.getDescription() != null ? b.getDescription() : "",
                        b.getComment()     != null ? b.getComment()     : "",
                        b.getCategoryId()  != null ? b.getCategoryId()  : ""
                });
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Error loading beers: " + ex.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Links this table to a form: when a row is clicked,
     * the form is automatically filled with the selected beer.
     */
    public void linkToForm(BeerFormPanel form) {
        this.linkedForm = form;
        form.setBeerTable(this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = getSelectedRow();
                if (row >= 0 && linkedForm != null) {
                    try {
                        int beerId = (int) tableModel.getValueAt(row, 0);
                        Beer beer = parent.getApplicationController().getBeerById(beerId);
                        linkedForm.beerToForm(beer);
                    } catch (ReadException ex) {
                        JOptionPane.showMessageDialog(parent,
                                "Read error: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public DefaultTableModel getTableModel() { return tableModel; }
}
