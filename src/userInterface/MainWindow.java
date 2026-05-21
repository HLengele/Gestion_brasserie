package userInterface;

import controller.ApplicationController;
import userInterface.MainMenu;
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
        this.setSize(900, 700); // Taille légèrement augmentée pour le confort du tableau des bières
        this.setLocationRelativeTo(null);

        // Instanciation du Contrôleur Applicatif (Façade)
        this.applicationController = new ApplicationController();

        this.container = this.getContentPane();
        this.container.setLayout(new BorderLayout());

        // Ajout de la barre de menu supérieure (on lui passe 'this' pour qu'elle puisse commander la fenêtre)
        this.setJMenuBar(new MainMenu(this));

        // Panneau affiché par défaut au démarrage (Accueil)
        this.currentPanel = new HomePanel();
        this.container.add(currentPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public ApplicationController getApplicationController() {
        return applicationController;
    }

    /**
     * Méthode essentielle pour changer dynamiquement le panneau central
     * Exemple : passer de l'accueil à la gestion des bières
     */
    public void changePanel(JPanel newPanel) {
        this.container.remove(currentPanel);
        this.currentPanel = newPanel;
        this.container.add(currentPanel, BorderLayout.CENTER);

        // On force Swing à recalculer les composants et à redessiner l'interface
        this.container.validate();
        this.container.repaint();
    }

    public static void main(String[] args) {
        // Lancement de l'application dans le thread Swing dédié
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}