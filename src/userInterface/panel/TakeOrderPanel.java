package userInterface.panel;

import exception.ReadException;
import model.*;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class TakeOrderPanel extends JPanel {

    private MainWindow parent;

    // Éléments de sélection
    private JComboBox<String> comboTable;
    private JComboBox<String> comboBeer;
    private JSpinner spinnerQuantity;
    private JButton btnAddToCart;

    // Panier
    private JTable tableCart;
    private DefaultTableModel cartModel;
    private JLabel lblTotal;
    private JButton btnSendOrder;

    // Données temporaires
    private ArrayList<LineOrder> currentCart = new ArrayList<>();
    private ArrayList<Beer> allBeers;
    private ArrayList<Table> allTables;

    public TakeOrderPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));

        // --- 1. SÉLECTION (Le premier écran) ---
        JPanel selectionPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Sélection des produits"));

        selectionPanel.add(new JLabel("Table :"));
        comboTable = new JComboBox<>();
        selectionPanel.add(comboTable);

        selectionPanel.add(new JLabel("Bière :"));
        comboBeer = new JComboBox<>();
        selectionPanel.add(comboBeer);

        selectionPanel.add(new JLabel("Quantité :"));
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        selectionPanel.add(spinnerQuantity);

        selectionPanel.add(new JLabel(""));
        btnAddToCart = new JButton("Ajouter au panier 🛒");
        selectionPanel.add(btnAddToCart);

        this.add(selectionPanel, BorderLayout.NORTH);

        // --- 2. PANIER (Le deuxième écran) ---
        String[] columns = {"ID Bière", "Nom", "Quantité", "Prix Unitaire", "Sous-Total"};
        cartModel = new DefaultTableModel(columns, 0);
        tableCart = new JTable(cartModel);

        JScrollPane scrollCart = new JScrollPane(tableCart);
        scrollCart.setBorder(BorderFactory.createTitledBorder("Panier"));
        this.add(scrollCart, BorderLayout.CENTER);

        // --- 3. VALIDATION ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total : 0.00 €");
        btnSendOrder = new JButton("Envoyer la commande ✅");

        bottomPanel.add(lblTotal);
        bottomPanel.add(btnSendOrder);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // --- Initialisation des données ---
        loadData();

        // --- Événements ---
        btnAddToCart.addActionListener(e -> addToCart());
        btnSendOrder.addActionListener(e -> sendOrder());
    }

    private void loadData() {
        try {
            allTables = parent.getApplicationController().getAllTables();
            allBeers = parent.getApplicationController().getAllBeers();

            comboTable.addItem("-- Choisir Table --");
            for(Table t : allTables) comboTable.addItem("Table " + t.getTableNumber());

            comboBeer.addItem("-- Choisir Bière --");
            for(Beer b : allBeers) comboBeer.addItem(b.getName() + " (" + b.getPrice() + "€)");
        } catch (ReadException e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement : " + e.getMessage());
        }
    }

    private void addToCart() {
        if(comboBeer.getSelectedIndex() == 0) return;

        Beer selectedBeer = allBeers.get(comboBeer.getSelectedIndex() - 1);
        int qty = (int) spinnerQuantity.getValue();

        // Ajout au modèle temporaire
        LineOrder line = new LineOrder(selectedBeer.getBeerId(), selectedBeer.getName(), qty, selectedBeer.getPrice());
        currentCart.add(line);

        // Mise à jour interface
        cartModel.addRow(new Object[]{selectedBeer.getBeerId(), selectedBeer.getName(), qty, selectedBeer.getPrice(), qty * selectedBeer.getPrice()});
        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        for(LineOrder l : currentCart) total += (l.getQuantity() * l.getRealPrice());
        lblTotal.setText(String.format("Total : %.2f €", total));
    }

    private void sendOrder() {
        if(comboTable.getSelectedIndex() == 0 || currentCart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Panier vide ou table non sélectionnée.");
            return;
        }

        // Création de l'objet Order
        int tableNum = allTables.get(comboTable.getSelectedIndex() - 1).getTableNumber();


        try {
            Order newOrder = new Order(null, LocalTime.now(), tableNum);

            // parent.getApplicationController().addOrder(newOrder);
            JOptionPane.showMessageDialog(this, "Commande envoyée pour la table " + tableNum);
            currentCart.clear();
            cartModel.setRowCount(0);
            updateTotal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }
}