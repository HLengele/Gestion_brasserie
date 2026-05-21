package userInterface.components;

import model.*;
import exception.*;
import userInterface.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TakeOrderPanel extends JPanel {
    private MainWindow parent;

    private JComboBox<Employee> comboEmployees;
    private JComboBox<Table> comboTables;
    private JComboBox<Beer> comboBeers;
    private JSpinner spinnerQuantity;
    private JButton btnValidate;

    public TakeOrderPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new GridLayout(5, 2, 10, 10));

        // Initialisation des composants
        comboEmployees = new JComboBox<>();
        comboTables    = new JComboBox<>();
        comboBeers     = new JComboBox<>();
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        btnValidate    = new JButton("Enregistrer la commande");

        // Agencement
        this.add(new JLabel("Choisir l'employé :"));
        this.add(comboEmployees);

        this.add(new JLabel("Choisir la table :"));
        this.add(comboTables);

        this.add(new JLabel("Choisir la bière :"));
        this.add(comboBeers);

        this.add(new JLabel("Quantité :"));
        this.add(spinnerQuantity);

        this.add(new JLabel("")); // case vide pour alignement
        this.add(btnValidate);

        // Chargement des données
        loadData();

        // Action bouton
        btnValidate.addActionListener(e -> performTakeOrder());
    }

    private void loadData() {
        try {
            ArrayList<Employee> employees = parent.getApplicationController().getAllEmployees();
            for (Employee emp : employees) {
                comboEmployees.addItem(emp);
            }

            ArrayList<Table> tables = parent.getApplicationController().getAllTables();
            for (Table t : tables) {
                comboTables.addItem(t);
            }

            ArrayList<Beer> beers = parent.getApplicationController().getAllBeers();
            for (Beer b : beers) {
                comboBeers.addItem(b);
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur de chargement des données : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performTakeOrder() {
        try {
            Employee selectedEmp  = (Employee) comboEmployees.getSelectedItem();
            Table    selectedTab  = (Table)    comboTables.getSelectedItem();
            Beer     selectedBeer = (Beer)     comboBeers.getSelectedItem();
            int      qty          = (int)      spinnerQuantity.getValue();

            if (selectedEmp == null || selectedTab == null || selectedBeer == null) {
                JOptionPane.showMessageDialog(parent, "Veuillez sélectionner tous les éléments.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double realPrice = selectedBeer.getPrice();

            Order newOrder = new Order(
                    0,
                    LocalDate.now(),
                    LocalTime.now(),
                    "En cours",
                    selectedTab.getTableNumber(),
                    selectedEmp.getEmployeeId()
            );

            parent.getApplicationController().addOrder(newOrder);

            JOptionPane.showMessageDialog(
                    parent,
                    "Commande enregistrée pour la Table n°" + selectedTab.getTableNumber()
                            + "\nBière : " + selectedBeer.getName()
                            + " x" + qty
                            + " — Total : " + String.format("%.2f", realPrice * qty) + " €",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Réinitialisation
            spinnerQuantity.setValue(1);

        } catch (AddOrderException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur BDD", JOptionPane.ERROR_MESSAGE);
        } catch (exception.NullValueException ex) {
            JOptionPane.showMessageDialog(parent, "Donnée invalide : " + ex.getMessage(), "Erreur Validation", JOptionPane.ERROR_MESSAGE);
        }
    }
}