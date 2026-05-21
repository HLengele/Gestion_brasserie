package userInterface.panel;

import model.Table;
import exception.ReadException;
import userInterface.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdditionPanel extends JPanel {
    private MainWindow parent;
    private JComboBox<Table> comboTables;
    private JButton btnCalculate;
    private JLabel lblResult;

    public AdditionPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        this.add(new JLabel("Numéro de la table :"));
        comboTables = new JComboBox<>();
        this.add(comboTables);

        btnCalculate = new JButton("Afficher l'addition");
        this.add(btnCalculate);

        lblResult = new JLabel("Total : 0.00 €");
        lblResult.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(lblResult);

        // Chargement des tables existantes
        loadTables();

        // Écouteur du bouton
        btnCalculate.addActionListener(e -> displayAddition());
    }

    private void loadTables() {
        try {
            ArrayList<Table> tables = parent.getApplicationController().getAllTables();
            for (Table t : tables) {
                comboTables.addItem(t);
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur de chargement des tables : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAddition() {
        Table selectedTable = (Table) comboTables.getSelectedItem();
        if (selectedTable != null) {
            try {
                // CORRECTION : Appel direct de la méthode exposée par la Façade (ApplicationController)
                double total = parent.getApplicationController().calculateTableAddition(selectedTable.getTableNumber());

                lblResult.setText(String.format("Total de la Table %d : %.2f €", selectedTable.getTableNumber(), total));

            } catch (ReadException ex) {
                JOptionPane.showMessageDialog(parent, "Erreur lors du calcul de l'addition : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}