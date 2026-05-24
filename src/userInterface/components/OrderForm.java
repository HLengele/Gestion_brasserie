package userInterface.components;

import exception.NullValueException;
import model.Order;
import userInterface.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Formulaire de commande simple (pas de CRUD ici).
 * Le CRUD est sur Beer. OrderForm sert juste à saisir/afficher une commande.
 */
public class OrderForm extends JPanel {

    private JTextField txtOrderId;
    private JTextField txtHour;
    private JSpinner   spinnerTableNumber;

    private Integer currentOrderId = null;

    public OrderForm() {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel lblId = new JLabel("ID Order :");
        txtOrderId = new JTextField();
        txtOrderId.setEditable(false);
        txtOrderId.setBackground(java.awt.Color.LIGHT_GRAY);

        JLabel lblHour = new JLabel("Hour (HH:mm:ss) :");
        txtHour = new JTextField();
        txtHour.setToolTipText("Format HH:mm:ss ");

        JLabel lblTable = new JLabel("Table Number :");
        spinnerTableNumber = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        this.add(lblId);
        this.add(txtOrderId);
        this.add(Box.createVerticalStrut(8));
        this.add(lblHour);
        this.add(txtHour);
        this.add(Box.createVerticalStrut(8));
        this.add(lblTable);
        this.add(spinnerTableNumber);

        orderToForm(null);
    }

    public void orderToForm(Order order) {
        if (order == null) {
            currentOrderId = null;
            txtOrderId.setText("");
            txtHour.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            spinnerTableNumber.setValue(1);
        } else {
            currentOrderId = order.getOrderId();
            txtOrderId.setText(String.valueOf(order.getOrderId()));
            txtHour.setText(order.getHour().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            spinnerTableNumber.setValue(order.getTableNumber());
        }
    }

    public Order formToOrder() throws NullValueException, IllegalArgumentException {
        LocalTime hour;
        try {
            hour = LocalTime.parse(txtHour.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("The time format is incorrect (HH:mm:ss required).");
        }
        int tableNumber = (int) spinnerTableNumber.getValue();
        int id = (currentOrderId == null) ? 0 : currentOrderId;
        return new Order(id, hour, tableNumber);
    }

    public Integer getCurrentOrderId() { return currentOrderId; }
    public void setCurrentOrderId(Integer id) { this.currentOrderId = id; }
}
