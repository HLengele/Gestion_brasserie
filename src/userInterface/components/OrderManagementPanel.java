package userInterface.components;

import userInterface.MainWindow;

import javax.swing.*;
import java.awt.*;

public class OrderManagementPanel extends JPanel {

    public OrderManagementPanel(MainWindow parent) {
        this.setLayout(new BorderLayout(20, 20));

        // 1. Instanciation du formulaire qui utilise uniquement des int
        OrderForm form = new OrderForm();
        this.add(form, BorderLayout.CENTER);

        // 2. Création de la barre d'outils (Sud)
        JPanel buttonPanel = new JPanel();

        // 3. Liaison en passant les références directes
        UpdateOrderButton updateButton = new UpdateOrderButton(form, parent);
        buttonPanel.add(updateButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}