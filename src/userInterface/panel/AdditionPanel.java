package userInterface.panel;

import exception.ReadException;
import model.Table;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class AdditionPanel extends JPanel {

    private MainWindow parent;
    private JComboBox<Table> comboTables;
    private JButton btnCalculate;
    private JLabel lblResult;

    public AdditionPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTable = new JLabel("Numéro de la table :");
        lblTable.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboTables = new JComboBox<>();
        comboTables.setMaximumSize(new Dimension(300, 30));
        comboTables.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCalculate = new JButton("Afficher l'addition");
        btnCalculate.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblResult = new JLabel("Total : 0.00 €");
        lblResult.setFont(new Font("Serif", Font.BOLD, 20));
        lblResult.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());
        this.add(lblTable);
        this.add(Box.createVerticalStrut(8));
        this.add(comboTables);
        this.add(Box.createVerticalStrut(12));
        this.add(btnCalculate);
        this.add(Box.createVerticalStrut(20));
        this.add(lblResult);
        this.add(Box.createVerticalGlue());

        loadTables();
        btnCalculate.addActionListener(e -> displayAddition());
    }

    private void loadTables() {
        try {
            ArrayList<Table> tables = parent.getApplicationController().getAllTables();
            for (Table t : tables) {
                comboTables.addItem(t);
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Erreur de chargement des tables : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAddition() {
        Table selectedTable = (Table) comboTables.getSelectedItem();
        if (selectedTable != null) {
            try {
                double total = parent.getApplicationController().calculateTableAddition(selectedTable.getTableNumber());
                lblResult.setText(String.format("Total de la Table %d : %.2f €", selectedTable.getTableNumber(), total));
            } catch (ReadException ex) {
                JOptionPane.showMessageDialog(parent,
                        "Erreur lors du calcul de l'addition : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
