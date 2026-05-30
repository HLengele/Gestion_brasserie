package userInterface.panel;

import exception.ReadException;
import model.Beer;
import model.LineOrder;
import model.Order;
import model.Table;
import userInterface.MainWindow;
import userInterface.components.OrderForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TakeOrderPanel extends JPanel {

    private MainWindow parent;

    private JComboBox<String> comboTable;
    private JComboBox<String> comboBeer;
    private JSpinner spinnerQuantity;
    private JButton btnAddToCart;

    private JTable tableCart;
    private DefaultTableModel cartModel;
    private JLabel lblTotal;
    private JButton btnSendOrder;
    private JButton btnClearCart;

    private ArrayList<LineOrder> currentCart = new ArrayList<>();
    private ArrayList<Beer>  allBeers;
    private ArrayList<Table> allTables;

    public TakeOrderPanel(MainWindow parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel selectionPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("1. Product selection"));

        selectionPanel.add(new JLabel("Table:"));
        comboTable = new JComboBox<>();
        selectionPanel.add(comboTable);

        selectionPanel.add(new JLabel("Beer:"));
        comboBeer = new JComboBox<>();
        selectionPanel.add(comboBeer);

        selectionPanel.add(new JLabel("Quantity:"));
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        selectionPanel.add(spinnerQuantity);

        selectionPanel.add(new JLabel(""));
        btnAddToCart = new JButton("Add to cart");
        selectionPanel.add(btnAddToCart);

        this.add(selectionPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("2. Order summary"));

        String[] columns = {"Beer ID", "Name", "Quantity", "Unit Price", "Subtotal"};
        cartModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableCart = new JTable(cartModel);
        centerPanel.add(new JScrollPane(tableCart), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        lblTotal = new JLabel("Total: 0.00 €");
        lblTotal.setFont(new Font("Serif", Font.BOLD, 16));

        btnClearCart = new JButton("Clear cart");
        btnSendOrder = new JButton("Send order");

        bottomPanel.add(btnClearCart);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(lblTotal);
        bottomPanel.add(Box.createHorizontalStrut(10));
        bottomPanel.add(btnSendOrder);

        centerPanel.add(bottomPanel, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);

        loadComboBoxData();
        btnAddToCart.addActionListener(e -> addToCart());
        btnClearCart.addActionListener(e -> clearCart());
        btnSendOrder.addActionListener(e -> sendOrder());
    }

    private void loadComboBoxData() {
        try {
            allTables = parent.getApplicationController().getAllTables();
            comboTable.addItem("-- Select a table --");
            for (Table t : allTables) comboTable.addItem("Table " + t.getTableNumber());

            allBeers = parent.getApplicationController().getAllBeers();
            comboBeer.addItem("-- Select a beer --");
            for (Beer b : allBeers) comboBeer.addItem(b.getName() + " - " + b.getPrice() + "€");
        } catch (ReadException ex) {
            JOptionPane.showMessageDialog(this,
                    "Loading error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToCart() {
        if (comboBeer.getSelectedIndex() == 0 || comboTable.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a table and a beer.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Beer selectedBeer = allBeers.get(comboBeer.getSelectedIndex() - 1);
        int qty = (int) spinnerQuantity.getValue();

        boolean found = false;
        for (LineOrder line : currentCart) {
            if (line.getBeerId() == selectedBeer.getBeerId()) {
                line.setQuantity(line.getQuantity() + qty);
                found = true;
                break;
            }
        }
        if (!found) {
            currentCart.add(new LineOrder(selectedBeer.getBeerId(), selectedBeer.getName(), qty, selectedBeer.getPrice()));
        }
        spinnerQuantity.setValue(1);
        refreshCartTable();
    }

    private void refreshCartTable() {
        cartModel.setRowCount(0);
        double total = 0;
        for (LineOrder line : currentCart) {
            double sub = line.getQuantity() * line.getRealPrice();
            total += sub;
            cartModel.addRow(new Object[]{
                    line.getBeerId(), line.getBeerName(), line.getQuantity(),
                    String.format("%.2f €", line.getRealPrice()),
                    String.format("%.2f €", sub)
            });
        }
        lblTotal.setText(String.format("Total: %.2f €", total));
    }

    private void clearCart() {
        currentCart.clear();
        refreshCartTable();
    }

    private void sendOrder() {
        if (comboTable.getSelectedIndex() == 0 || currentCart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty cart or no table selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int tableNum = allTables.get(comboTable.getSelectedIndex() - 1).getTableNumber();
            Order newOrder = new Order(0, java.time.LocalTime.now(), tableNum);
            int newOrderId = parent.getApplicationController().addOrder(newOrder);
            for (LineOrder line : currentCart) {
                parent.getApplicationController().addLineOrder(newOrderId, line.getBeerId(), line.getQuantity(), line.getRealPrice());
            }
            JOptionPane.showMessageDialog(this, "Order sent for table " + tableNum);
            clearCart();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}