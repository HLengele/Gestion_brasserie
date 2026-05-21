package userInterface.panel;

import model.*;
import exception.*;
import userInterface.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TakeOrderPanel extends JPanel {
    private MainWindow parent;

    private JComboBox<Employee> comboEmployees;
    private JComboBox<Table>    comboTables;
    private JComboBox<Beer>     comboBeers;
    private JSpinner            spinnerQuantity;
    private JButton             btnValidate;

    public TakeOrderPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new GridLayout(5, 2, 10, 10));

        comboEmployees  = new JComboBox<>();
        comboTables     = new JComboBox<>();
        comboBeers      = new JComboBox<>();
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        btnValidate     = new JButton("Enregistrer la commande");

        this.add(new JLabel("Choisir l'employé :"));
        this.add(comboEmployees);

        this.add(new JLabel("Choisir la table :"));
        this.add(comboTables);

        this.add(new JLabel("Choisir la bière :"));
        this.add(comboBeers);

        this.add(new JLabel("Quantité :"));
        this.add(spinnerQuantity);

        this.add(new JLabel(""));
        this.add(btnValidate);

        loadData();

        btnValidate.addActionListener(e -> performTakeOrder());
    }

    private void loadData() {
        try {
            ArrayList<Employee> employees = parent.getApplicationController().getAllEmployees();
            for (Employee emp : employees) comboEmployees.addItem(emp);

            ArrayList<Table> tables = parent.getApplicationController().getAllTables();
            for (Table t : tables) comboTables.addItem(t);

            ArrayList<Beer> beers = parent.getApplicationController().getAllBeers();
            for (Beer b : beers) comboBeers.addItem(b);

        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur de chargement : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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

            // ── 1. Créer la commande dans Order ───────────────────────────────
            Order newOrder = new Order(
                    0,
                    LocalDate.now(),
                    LocalTime.now(),
                    "En cours",
                    selectedTab.getTableNumber(),
                    selectedEmp.getEmployeeId()
            );
            parent.getApplicationController().addOrder(newOrder);

            // ── 2. Récupérer l'orderId généré automatiquement ─────────────────
            Connection connection = dataAccess.SingletonConnection.getInstance();
            Statement stmtLastId = connection.createStatement();
            ResultSet rs = stmtLastId.executeQuery("SELECT LAST_INSERT_ID() AS lastId");
            int newOrderId = 0;
            if (rs.next()) newOrderId = rs.getInt("lastId");

            // ── 3. Insérer dans Order_Line ────────────────────────────────────
            double unitPrice = selectedBeer.getPrice();
            String sqlLine = "INSERT INTO Order_Line (orderId, beerId, quantity, unit_price) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtLine = connection.prepareStatement(sqlLine);
            stmtLine.setInt(1, newOrderId);
            stmtLine.setInt(2, selectedBeer.getBeerId());
            stmtLine.setInt(3, qty);
            stmtLine.setDouble(4, unitPrice);
            stmtLine.executeUpdate();

            // ── 4. Confirmation ───────────────────────────────────────────────
            JOptionPane.showMessageDialog(
                    parent,
                    "Commande enregistrée !\n"
                            + "Table n°" + selectedTab.getTableNumber() + "\n"
                            + "Bière : " + selectedBeer.getName() + " x" + qty + "\n"
                            + "Total : " + String.format("%.2f", unitPrice * qty) + " €",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // ── 5. Réinitialisation ───────────────────────────────────────────
            spinnerQuantity.setValue(1);

        } catch (AddOrderException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur ajout commande : " + ex.getMessage(), "Erreur BDD", JOptionPane.ERROR_MESSAGE);
        } catch (NullValueException ex) {
            JOptionPane.showMessageDialog(parent, "Donnée invalide : " + ex.getMessage(), "Erreur Validation", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur SQL : " + ex.getMessage(), "Erreur BDD", JOptionPane.ERROR_MESSAGE);
        }
    }
}