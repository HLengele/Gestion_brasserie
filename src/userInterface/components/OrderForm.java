package userInterface.components;

import exception.NullValueException;
import model.Order;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrderForm extends JPanel {

    private JTextField txtDate, txtHour, txtStatus, txtTableNumber, txtEmployeeId;
    // Remplacement d'Integer par int (0 signifie "aucune commande sélectionnée")
    private int currentOrderId = 0;

    public OrderForm() {
        this.setLayout(new GridLayout(5, 2, 10, 10));

        this.add(new JLabel("Date (AAAA-MM-JJ) :"));
        txtDate = new JTextField();
        this.add(txtDate);

        this.add(new JLabel("Heure (HH:MM:SS) :"));
        txtHour = new JTextField();
        this.add(txtHour);

        this.add(new JLabel("Statut :"));
        txtStatus = new JTextField();
        this.add(txtStatus);

        this.add(new JLabel("Numéro de Table :"));
        txtTableNumber = new JTextField();
        this.add(txtTableNumber);

        this.add(new JLabel("ID Employé :"));
        txtEmployeeId = new JTextField();
        this.add(txtEmployeeId);
    }

    // Retourne un int primitif
    public int getCurrentOrderId() {
        return currentOrderId;
    }

    // Reçoit un int primitif
    public void setCurrentOrderId(int id) {
        this.currentOrderId = id;
    }

    public void orderToForm(Order order) {
        this.currentOrderId = order.getOrderId();
        this.txtDate.setText(order.getOrderDate().toString());
        this.txtHour.setText(order.getHour().toString());
        this.txtStatus.setText(order.getStatus());
        this.txtTableNumber.setText(String.valueOf(order.getTableNumber()));
        this.txtEmployeeId.setText(String.valueOf(order.getEmployeeId()));
    }

    public Order formToOrder() throws NullValueException, NumberFormatException {
        LocalDate date = LocalDate.parse(txtDate.getText().trim());
        LocalTime hour = LocalTime.parse(txtHour.getText().trim());
        String status = txtStatus.getText().trim();
        int tableNum = Integer.parseInt(txtTableNumber.getText().trim());
        int empId = Integer.parseInt(txtEmployeeId.getText().trim());

        // currentOrderId est directement un int primitif
        return new Order(currentOrderId, date, hour, status, tableNum, empId);
    }

    public void clearForm() {
        this.currentOrderId = 0; // Réinitialisation à 0
        this.txtDate.setText("");
        this.txtHour.setText("");
        this.txtStatus.setText("");
        this.txtTableNumber.setText("");
        this.txtEmployeeId.setText("");
    }
}