package userInterface;

// --- VOS IMPORTS ---
// Si votre ApplicationController est dans un autre package (ex: business), ajustez cette ligne :
import controller.ApplicationController;

import userInterface.components.HomePanel;
import userInterface.panel.BeerManagementPanel;
import userInterface.panel.TakeOrderPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    // Déclaration de votre contrôleur de base de données
    private ApplicationController applicationController;

    public MainWindow() {
        // 1. Initialisation du contrôleur (INDISPENSABLE avant d'afficher quoi que ce soit)
        this.applicationController = new ApplicationController();

        // 2. Configuration globale de la fenêtre
        this.setTitle("Gestion de la Brasserie");
        this.setSize(1100, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Permet de centrer la fenêtre sur l'écran

        // 3. Affichage de l'accueil par défaut au démarrage
        setMainPanel(new HomePanel());

        // 4. Initialisation de la barre de navigation
        initMenu();

        // 5. AFFICHAGE DE LA FENÊTRE (La ligne qui manquait pour que l'appli démarre !)
        this.setVisible(true);
    }

    /**
     * Crée et configure la barre de menu supérieure
     */
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuNavigation = new JMenu("Navigation");

        // Création des boutons du menu
        JMenuItem itemHome = new JMenuItem("🏠 Accueil");
        JMenuItem itemBeers = new JMenuItem("🍺 Gestion des Bières");
        JMenuItem itemOrders = new JMenuItem("📝 Tâches Métiers (Commandes)");

        // Ajout des actions de navigation (changement de panneaux)
        itemHome.addActionListener(e -> setMainPanel(new HomePanel()));
        itemBeers.addActionListener(e -> setMainPanel(new BeerManagementPanel(this)));
        itemOrders.addActionListener(e -> setMainPanel(new TakeOrderPanel(this)));

        // Assemblage des éléments dans le menu
        menuNavigation.add(itemHome);
        menuNavigation.addSeparator(); // Petite ligne esthétique
        menuNavigation.add(itemBeers);
        menuNavigation.add(itemOrders);

        // Ajout du menu à la barre, et de la barre à la fenêtre
        menuBar.add(menuNavigation);
        this.setJMenuBar(menuBar);
    }

    /**
     * Méthode permettant de vider le centre de la fenêtre
     * pour y injecter le panneau de votre choix de manière fluide.
     */
    public void setMainPanel(JPanel newPanel) {
        this.getContentPane().removeAll(); // On nettoie l'ancien contenu
        this.getContentPane().add(newPanel, BorderLayout.CENTER); // On ajoute la nouvelle page
        this.getContentPane().revalidate(); // On recalcule l'affichage
        this.getContentPane().repaint();    // On redessine l'interface
    }

    /**
     * Permet aux différents panneaux (BeerManagementPanel, TakeOrderPanel...)
     * de récupérer le contrôleur pour communiquer avec la base de données.
     */
    public ApplicationController getApplicationController() {
        return this.applicationController;
    }
}