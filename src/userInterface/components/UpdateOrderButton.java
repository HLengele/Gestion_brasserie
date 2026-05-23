package userInterface.components;

import model.Order;
import userInterface.MainWindow;
import userInterface.components.OrderForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateOrderButton extends JButton {
    private OrderForm orderForm;
    private MainWindow parent; // <-- Assurez-vous que c'est bien "MainWindow" ici

    // Le constructeur DOIT prendre (OrderForm, MainWindow)
    public UpdateOrderButton(OrderForm orderForm, MainWindow parent) {
        super("Mettre à jour");
        this.orderForm = orderForm;
        this.parent = parent;
        this.addActionListener(new UpdateListener());
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Votre code de clic (que l'on a écrit au message précédent)
            if (orderForm.getCurrentOrderId() > 0) {
                try {
                    Order orderToUpdate = orderForm.formToOrder();
                    parent.getApplicationController().updateOrder(orderToUpdate);
                    JOptionPane.showMessageDialog(parent, "Commande mise à jour !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Sélectionnez une commande d'abord.");
            }
        }
    }
}