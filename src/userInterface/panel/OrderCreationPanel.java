package userInterface.panel;

import exception.ReadException;
import model.Beer;
import model.LineOrder;
import model.Order;
import model.Table; // Assurez-vous d'avoir un modèle Table
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class OrderCreationPanel extends JPanel {

    private MainWindow parent;

    // Éléments du formulaire (Haut)
    private JComboBox<String> comboTable;
    private JComboBox<String> comboBeer;
    private JSpinner spinnerQuantity;
    private JButton btnAddToCart;

    // Éléments du panier (Bas)
    private JTable tableCart;
    private DefaultTableModel cartModel;
    private JLabel lblTotal;
    private JButton btnSendOrder;

    // Stockage temporaire de la commande en cours
    private ArrayList<LineOrder> currentCart;
    private ArrayList<Beer> availableBeers;
    private ArrayList<Table> availableTables;

    public OrderCreationPanel(MainWindow parent) {
        this.parent = parent;
        this.currentCart = new ArrayList<>();
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. PARTIE HAUTE : AJOUT DES PRODUITS ---
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("1. Sélection des produits"));

        topPanel.add(new JLabel("Table :"));
        comboTable = new JComboBox<>();
        topPanel.add(comboTable);

        topPanel.add(new JLabel("Bière :"));
        comboBeer = new JComboBox<>();
        topPanel.add(comboBeer);

        topPanel.add(new JLabel("Quantité :"));
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        topPanel.add(spinnerQuantity);

        topPanel.add(new JLabel("")); // Espace vide
        btnAddToCart = new JButton("Ajouter au panier 🛒");
        topPanel.add(btnAddToCart);

        this.add(topPanel, BorderLayout.NORTH);

        // --- 2. PARTIE CENTRALE : LE PANIER (LE DEUXIÈME ÉCRAN) ---
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("2. Récapitulatif de la commande (Panier)"));

        String[] columns = {"ID Bière", "Nom", "Quantité", "Prix Unitaire", "Sous-Total"};
        cartModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCart = new JTable(cartModel);
        centerPanel.add(new JScrollPane(tableCart), BorderLayout.CENTER);

        // --- 3. PARTIE BASSE : VALIDATION ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        lblTotal = new JLabel("Total : 0.00 €");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        btnSendOrder = new JButton("Envoyer la commande ✅");
        btnSendOrder.setBackground(new Color(46, 204, 113));
        btnSendOrder.setForeground(Color.WHITE);

        JButton btnClearCart = new JButton("Vider le panier 🗑️");

        bottomPanel.add(btnClearCart);
        bottomPanel.add(lblTotal);
        bottomPanel.add(btnSendOrder);

        centerPanel.add(bottomPanel, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);

        // --- CHARGEMENT DES DONNÉES ---
        loadComboBoxData();

        // --- ÉVÉNEMENTS ---

        // Ajouter au panier
        btnAddToCart.addActionListener(e -> addToCart());

        // Vider le panier
        btnClearCart.addActionListener(e -> clearCart());

        // Envoyer la commande (Logique DB à implémenter ensuite)
        btnSendOrder.addActionListener(e -> sendOrder());
    }

    private void loadComboBoxData() {
        try {
            // Charger les tables
            availableTables = parent.getApplicationController().getAllTables();
            comboTable.addItem("-- Sélectionner une table --");
            for (Table t : availableTables) {
                comboTable.addItem("Table " + t.getTableNumber()); // Ajustez selon votre modèle Table
            }

            // Charger les bières
            availableBeers = parent.getApplicationController().getAllBeers();
            comboBeer.addItem("-- Sélectionner une bière --");
            for (Beer b : availableBeers) {
                comboBeer.addItem(b.getName() + " - " + b.getPrice() + "€ (" + b.getBeerId() + ")");
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToCart() {
        if (comboBeer.getSelectedIndex() == 0 || comboTable.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une table et une bière.", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Récupérer la bière sélectionnée
        int beerIndex = comboBeer.getSelectedIndex() - 1;
        Beer selectedBeer = availableBeers.get(beerIndex);
        int qty = (int) spinnerQuantity.getValue();

        // Vérifier si la bière est déjà dans le panier pour simplement augmenter la quantité
        boolean found = false;
        for (LineOrder line : currentCart) {
            if (line.getBeerId() == selectedBeer.getBeerId()) {
                line.setQuantity(line.getQuantity() + qty);
                found = true;
                break;
            }
        }

        // Si elle n'y est pas, on l'ajoute
        if (!found) {
            LineOrder newLine = new LineOrder(selectedBeer.getBeerId(), selectedBeer.getName(), qty, selectedBeer.getPrice());
            currentCart.add(newLine);
        }

        // Remettre la quantité à 1 par défaut
        spinnerQuantity.setValue(1);
        refreshCartTable();
    }

    private void refreshCartTable() {
        cartModel.setRowCount(0);
        double grandTotal = 0.0;

        for (LineOrder line : currentCart) {
            double subTotal = line.getQuantity() * line.getRealPrice();
            grandTotal += subTotal;

            Object[] row = {
                    line.getBeerId(),
                    line.getBeerName(),
                    line.getQuantity(),
                    String.format("%.2f €", line.getRealPrice()),
                    String.format("%.2f €", subTotal)
            };
            cartModel.addRow(row);
        }

        lblTotal.setText(String.format("Total : %.2f €", grandTotal));
    }

    private void clearCart() {
        currentCart.clear();
        refreshCartTable();
    }

    private void sendOrder() {
        if (comboTable.getSelectedIndex() == 0 || currentCart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Panier vide ou table non sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int tableNum = availableTables.get(comboTable.getSelectedIndex() - 1).getTableNumber();

            // 1. Créer l'entête de la commande et récupérer l'ID généré
            Order newOrder = new Order(0, java.time.LocalTime.now(), tableNum);
            int newOrderId = parent.getApplicationController().addOrder(newOrder);

            // 2. Enregistrer chaque ligne du panier
            for (LineOrder line : currentCart) {
                parent.getApplicationController().addLineOrder(
                        newOrderId,
                        line.getBeerId(),
                        line.getQuantity(),
                        line.getRealPrice()
                );
            }

            JOptionPane.showMessageDialog(this, "Commande envoyée pour la table " + tableNum + " ✅");
            clearCart();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}