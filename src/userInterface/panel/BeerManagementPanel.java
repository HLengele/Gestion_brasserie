package userInterface.panel;

import exception.ReadException;
import model.Beer;
import model.Category;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BeerManagementPanel extends JPanel {

    private MainWindow parent;
    private JTable tableBeers;
    private DefaultTableModel tableModel;
    private BeerFormPanel beerForm;

    public BeerManagementPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));

        // 1. Tableau (Read)
        initTable();
        JScrollPane scrollPane = new JScrollPane(tableBeers);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Bières"));
        this.add(scrollPane, BorderLayout.CENTER);

        // 2. Formulaire
        JPanel bottomPanel = new JPanel(new BorderLayout());
        beerForm = new BeerFormPanel();
        beerForm.setBorder(BorderFactory.createTitledBorder("Détails de la Bière"));
        bottomPanel.add(beerForm, BorderLayout.CENTER);

        // --- CHARGEMENT DES CATÉGORIES DANS LA JCOMBOBOX ---
        try {
            ArrayList<Category> categories = parent.getApplicationController().getAllCategories();
            beerForm.populateCategories(categories);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Erreur de chargement des catégories : " + ex.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
        }

        // 3. Boutons CRUD
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Ajouter");
        JButton btnUpdate = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");
        JButton btnClear = new JButton("Vider le formulaire");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // --- ÉVÉNEMENTS ---

        // Clic sur le tableau
        tableBeers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableBeers.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int beerId = (int) tableModel.getValueAt(selectedRow, 0);
                        Beer beer = parent.getApplicationController().getBeerById(beerId);
                        beerForm.beerToForm(beer);
                    } catch (ReadException ex) {
                        JOptionPane.showMessageDialog(parent, "Erreur de lecture : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Action AJOUTER
        btnAdd.addActionListener(e -> {
            try {
                beerForm.setCurrentBeerId(null);
                Beer newBeer = beerForm.formToBeer();
                parent.getApplicationController().addBeer(newBeer);
                JOptionPane.showMessageDialog(parent, "Bière ajoutée avec succès !");
                refreshTable();
                beerForm.beerToForm(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action MODIFIER
        btnUpdate.addActionListener(e -> {
            try {
                Beer beerToUpdate = beerForm.formToBeer();
                if (beerToUpdate.getBeerId() != null && beerToUpdate.getBeerId() != 0) {
                    parent.getApplicationController().updateBeer(beerToUpdate);
                    JOptionPane.showMessageDialog(parent, "Bière mise à jour !");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(parent, "Veuillez sélectionner une bière à modifier.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Erreur de mise à jour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action SUPPRIMER
        btnDelete.addActionListener(e -> {
            int selectedRow = tableBeers.getSelectedRow();
            if (selectedRow >= 0) {
                int beerId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(parent, "Voulez-vous vraiment supprimer cette bière ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        parent.getApplicationController().deleteBeer(beerId);
                        JOptionPane.showMessageDialog(parent, "Bière supprimée !");
                        refreshTable();
                        beerForm.beerToForm(null);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, "Erreur de suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Veuillez sélectionner une bière à supprimer.");
            }
        });

        // Action VIDER
        btnClear.addActionListener(e -> beerForm.beerToForm(null));
    }

    private void initTable() {
        String[] columns = {"ID", "Nom", "Couleur", "Prix", "Alcool", "Lancement", "Description", "Commentaire", "Catégorie"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableBeers = new JTable(tableModel);

        tableBeers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < tableBeers.getColumnCount(); i++) {
            tableBeers.getColumnModel().getColumn(i).setPreferredWidth(110);
        }

        tableBeers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            ArrayList<Beer> beers = parent.getApplicationController().getAllBeers();
            for (Beer b : beers) {
                Object[] row = {
                        b.getBeerId(),
                        b.getName(),      // Colonne 1 : Nom
                        b.getColor(),     // Colonne 2 : Couleur
                        String.format("%.2f €", b.getPrice() != null ? b.getPrice() : 0.0),
                        (b.getContainsAlcool() != null && b.getContainsAlcool()) ? "Oui" : "Non",
                        b.getMarketLaunchDate() != null ? b.getMarketLaunchDate() : "N/A",
                        b.getDescription() != null ? b.getDescription() : "",
                        b.getComment() != null ? b.getComment() : "",
                        b.getCategoryId() != null ? b.getCategoryId() : ""
                };
                tableModel.addRow(row);
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur de chargement des bières : " + ex.getMessage(), "Erreur BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}