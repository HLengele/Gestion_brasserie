package userInterface;

import userInterface.components.AdditionPanel;
import userInterface.components.HomePanel;
import userInterface.components.OrderManagementPanel;
import userInterface.components.TakeOrderPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainMenu extends JMenuBar {
    private MainWindow parent;
    private JMenu appMenu, commandMenu;
    private JMenuItem home, exit, editCommands;

    public MainMenu(MainWindow parent) {
        this.parent = parent;

        // Menu Application
        appMenu = new JMenu("Application");
        appMenu.setMnemonic('A');
        this.add(appMenu);

        home = new JMenuItem("Accueil");
        home.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        home.addActionListener(new ChangePanelListener("Home"));
        appMenu.add(home);

        exit = new JMenuItem("Quitter");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exit.addActionListener(e -> System.exit(0));
        appMenu.add(exit);

        // Menu Commandes
        commandMenu = new JMenu("Commandes");
        commandMenu.setMnemonic('C');
        this.add(commandMenu);

        editCommands = new JMenuItem("Gérer les commandes");
        editCommands.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        editCommands.addActionListener(new ChangePanelListener("Edit"));
        commandMenu.add(editCommands);

        JMenu businessTasksMenu = new JMenu("Tâches Métiers");
        businessTasksMenu.setMnemonic('M');
        this.add(businessTasksMenu);

        JMenuItem itemTakeOrder = new JMenuItem("Prendre commande");
        itemTakeOrder.addActionListener(e -> parent.changePanel(new TakeOrderPanel(parent)));
        businessTasksMenu.add(itemTakeOrder);

        JMenuItem itemAddition = new JMenuItem("Calculer addition");
        itemAddition.addActionListener(e -> parent.changePanel(new AdditionPanel(parent)));
        businessTasksMenu.add(itemAddition);
    }

    private class ChangePanelListener implements ActionListener {
        private String panelName;

        public ChangePanelListener(String panelName) {
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("Home".equals(panelName)) {
                parent.changePanel(new HomePanel());
            } else if ("Edit".equals(panelName)) {
                parent.changePanel(new OrderManagementPanel(parent));
            }
        }
    }
}