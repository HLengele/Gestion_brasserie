package userInterface.components;

import exception.ReadException;
import model.Order;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class OrderManagementPanel extends JPanel {

    private MainWindow parent;
    private JTable tableOrders;
    private DefaultTableModel tableModel;
    private OrderForm orderForm;

    public OrderManagementPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));

        // 1. READ : Création du tableau pour lister toutes les commandes
        initTable();
        JScrollPane scrollPane = new JScrollPane(tableOrders);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Commandes (Read)"));
        this.add(scrollPane, BorderLayout.CENTER);

        // 2. FORMULAIRE : Utilisation de ton OrderForm existant
        JPanel bottomPanel = new JPanel(new BorderLayout());
        orderForm = new OrderForm();
        orderForm.setBorder(BorderFactory.createTitledBorder("Détails de la Commande"));
        bottomPanel.add(orderForm, BorderLayout.CENTER);

        // 3. BOUTONS : Create, Update, Delete
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Ajouter (Create)");
        JButton btnUpdate = new JButton("Modifier (Update)");
        JButton btnDelete = new JButton("Supprimer (Delete)");
        JButton btnClear = new JButton("Vider le formulaire");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // ── AJOUT DES ÉCOUTEURS D'ÉVÉNEMENTS (ACTIONS) ──

        // Remplir le formulaire quand on clique sur une ligne du tableau
        tableOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableOrders.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
                        Order order = parent.getApplicationController().getOrderById(orderId);
                        orderForm.orderToForm(order);
                    } catch (ReadException ex) {
                        JOptionPane.showMessageDialog(parent, "Erreur de lecture : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Action CREATE (Ajouter)
        btnAdd.addActionListener(e -> {
            try {
                // Force l'ID à 0 pour indiquer à la BDD qu'il s'agit d'une nouvelle insertion
                orderForm.setCurrentOrderId(0);
                Order newOrder = orderForm.formToOrder();
                parent.getApplicationController().addOrder(newOrder);
                JOptionPane.showMessageDialog(parent, "Commande ajoutée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                orderForm.clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action UPDATE (Modifier)
        btnUpdate.addActionListener(e -> {
            if (orderForm.getCurrentOrderId() > 0) {
                try {
                    Order orderToUpdate = orderForm.formToOrder();
                    parent.getApplicationController().updateOrder(orderToUpdate);
                    JOptionPane.showMessageDialog(parent, "Commande mise à jour avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                    orderForm.clearForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Erreur lors de la mise à jour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Veuillez sélectionner une commande dans le tableau pour la modifier.", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action DELETE (Supprimer)
        btnDelete.addActionListener(e -> {
            int selectedRow = tableOrders.getSelectedRow();
            if (selectedRow >= 0) {
                int orderId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(parent, "Voulez-vous vraiment supprimer cette commande ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        parent.getApplicationController().deleteOrder(orderId);
                        JOptionPane.showMessageDialog(parent, "Commande supprimée !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        refreshTable();
                        orderForm.clearForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Veuillez sélectionner une commande dans le tableau pour la supprimer.", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action CLEAR (Vider)
        btnClear.addActionListener(e -> orderForm.clearForm());
    }

    // ── MÉTHODES UTILITAIRES POUR LE TABLEAU ──

    private void initTable() {
        String[] columns = {"ID", "Date", "Heure", "Statut", "Table", "ID Employé"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche l'édition directe dans les cellules du tableau
            }
        };
        tableOrders = new JTable(tableModel);
        tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Vide le tableau existant
        try {
            ArrayList<Order> orders = parent.getApplicationController().getAllOrders();
            for (Order o : orders) {
                Object[] row = {
                        o.getOrderId(),
                        o.getOrderDate(),
                        o.getHour(),
                        o.getStatus(),
                        o.getTableNumber(),
                        o.getEmployeeId()
                };
                tableModel.addRow(row);
            }
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(parent, "Erreur lors du chargement des commandes : " + ex.getMessage(), "Erreur BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}