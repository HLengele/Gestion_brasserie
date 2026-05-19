package userInterface;

import controller.ApplicationController;
import userInterface.components.HomePanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private ApplicationController applicationController;
    private Container container;
    private JPanel currentPanel;

    public MainWindow() {
        super("Gestion de la Brasserie");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Instanciation de la Façade (Contrôleur)
        this.applicationController = new ApplicationController();

        this.container = this.getContentPane();
        this.container.setLayout(new BorderLayout());

        // Ajout du menu global
        this.setJMenuBar(new MainMenu(this));

        // Panneau par défaut (Accueil)
        this.currentPanel = new HomePanel();
        this.container.add(currentPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public ApplicationController getApplicationController() {
        return applicationController;
    }

    // Méthode permettant de switcher dynamiquement de panneau (ex: passer du Home à l'Edition)
    public void changePanel(JPanel newPanel) {
        this.container.remove(currentPanel);
        this.currentPanel = newPanel;
        this.container.add(currentPanel, BorderLayout.CENTER);
        this.container.validate();
        this.container.repaint();
    }

    public static void main(String[] args) {
        // Lancement de l'application
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}