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

public class BeerManagementPanel extends JPanel {

    private MainWindow parent;
    private BeerTable     beerTable;
    private BeerFormPanel beerForm;

    public BeerManagementPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        beerTable = new BeerTable(parent);
        JScrollPane scrollPane = new JScrollPane(beerTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Beer List"));
        // J'ai retiré le setPreferredSize ici : le CENTER s'étire automatiquement
        // pour remplir tout l'espace disponible, ce qui est parfait pour une table.
        this.add(scrollPane, BorderLayout.CENTER);

        beerForm = new BeerFormPanel(parent);
        beerForm.setBorder(BorderFactory.createTitledBorder("Beer Details"));

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
        // On modifie la dimension : largeur 0 (pour s'adapter à la fenêtre), hauteur 350
        formScroll.setPreferredSize(new Dimension(0, 500));

        // CORRECTION ICI : On place le formulaire au SUD (en bas) au lieu de l'EST
        this.add(formScroll, BorderLayout.SOUTH);

        beerTable.linkToForm(beerForm);
    }
}