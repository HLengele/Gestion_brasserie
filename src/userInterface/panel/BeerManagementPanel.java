package userInterface.panel;

import exception.ReadException;
import model.Category;
import userInterface.MainWindow;
import userInterface.components.BeerFormPanel;
import userInterface.components.BeerTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panneau de gestion des bières.
 * Utilise BeerFormPanel (avec boutons CRUD dédiés) et BeerTable (avec loadTable()),
 * reliés via beerTable.linkToForm() — exactement comme EditPanel dans UserInterface2.
 */
public class BeerManagementPanel extends JPanel {

    private MainWindow parent;
    private BeerTable     beerTable;
    private BeerFormPanel beerForm;

    public BeerManagementPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Tableau (WEST) ---
        beerTable = new BeerTable(parent);
        JScrollPane scrollPane = new JScrollPane(beerTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Beer List"));
        scrollPane.setPreferredSize(new Dimension(700, 0));
        this.add(scrollPane, BorderLayout.CENTER);

        // --- Formulaire + boutons CRUD (EAST) ---
        beerForm = new BeerFormPanel(parent);
        beerForm.setBorder(BorderFactory.createTitledBorder("Beer Details"));

        // Chargement des catégories
        try {
            ArrayList<Category> categories = parent.getApplicationController().getAllCategories();
            beerForm.populateCategories(categories);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent,
                    "Warning: unable to load categories. " + ex.getMessage(),
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }

        JScrollPane formScroll = new JScrollPane(beerForm);
        formScroll.setBorder(null);
        formScroll.setPreferredSize(new Dimension(320, 0));
        this.add(formScroll, BorderLayout.EAST);

        // Lien bidirectionnel tableau <-> formulaire (pattern UI2)
        beerTable.linkToForm(beerForm);
    }
}
