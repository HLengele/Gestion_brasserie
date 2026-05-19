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
    private JTextField txtRealPrice;
    private JButton btnValidate;

    public TakeOrderPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new GridLayout(6, 2, 10, 10));

        // 1. Initialisation des composants graphiques
        comboEmployees = new JComboBox<>();
        comboTables = new JComboBox<>();
        comboBeers = new JComboBox<>();
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        txtRealPrice = new JTextField();
        btnValidate = new JButton("Enregistrer la commande");

        // 2. Agencement des composants
        this.add(new JLabel("Choisir l'employé :"));
        this.add(comboEmployees);

        this.add(new JLabel("Choisir la table :"));
        this.add(comboTables);

        this.add(new JLabel("Choisir la bière :"));
        this.add(comboBeers);

        this.add(new JLabel("Quantité :"));
        this.add(spinnerQuantity);

        this.add(new JLabel("Prix Réel (€) :"));
        this.add(txtRealPrice);

        this.add(new JLabel("")); // Case vide pour alignement
        this.add(btnValidate);

        // 3. Chargement des données depuis le contrôleur
        loadData();

        // 4. Action de validation
        btnValidate.addActionListener(e -> performTakeOrder());
    }

    private void loadData() {
        try {
            // Remplissage du JComboBox des employés
            ArrayList<Employee> employees = parent.getApplicationController().getAllEmployees();
            for (Employee emp : employees) {
                comboEmployees.addItem(emp);
            }

            // Remplissage du JComboBox des tables
            ArrayList<Table> tables = parent.getApplicationController().getAllTables();
            for (Table t : tables) {
                comboTables.addItem(t);
            }

            // Remplissage du JComboBox des bières
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
            Employee selectedEmp = (Employee) comboEmployees.getSelectedItem();
            Table selectedTab = (Table) comboTables.getSelectedItem();
            Beer selectedBeer = (Beer) comboBeers.getSelectedItem();
            int qty = (int) spinnerQuantity.getValue();
            double realPrice = Double.parseDouble(txtRealPrice.getText().trim());

            if (selectedEmp == null || selectedTab == null || selectedBeer == null) {
                JOptionPane.showMessageDialog(parent, "Veuillez sélectionner tous les éléments.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ── CORRECTION ICI : Correspondance exacte avec votre constructeur ──
            // 1. int orderId       -> 0
            // 2. LocalDate date    -> LocalDate.now()
            // 3. LocalTime hour    -> LocalTime.now()
            // 4. String status     -> "En cours"
            // 5. int tableNumber   -> selectedTab.getTableNumber()
            // 6. int employeeId    -> selectedEmp.getEmployeeId()
            Order newOrder = new Order(
                    0,
                    LocalDate.now(),
                    LocalTime.now(),
                    "En cours",
                    selectedTab.getTableNumber(),
                    selectedEmp.getEmployeeId()
            );

            // Envoi de la commande principale au contrôleur (Façade)
            parent.getApplicationController().addOrder(newOrder);

            JOptionPane.showMessageDialog(parent, "Commande enregistrée avec succès pour la Table n°" + selectedTab.getTableNumber(), "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Réinitialisation des composants graphiques
            txtRealPrice.setText("");
            spinnerQuantity.setValue(1);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Le prix réel doit être un nombre valide.", "Erreur Format", JOptionPane.ERROR_MESSAGE);
        } catch (AddOrderException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur lors de l'ajout de la commande : " + ex.getMessage(), "Erreur BDD", JOptionPane.ERROR_MESSAGE);
        } catch (exception.NullValueException ex) {
            // Gestion de votre exception métier si une donnée obligatoire est manquante
            JOptionPane.showMessageDialog(parent, "Donnée invalide : " + ex.getMessage(), "Erreur Validation", JOptionPane.ERROR_MESSAGE);
        }
    }
}