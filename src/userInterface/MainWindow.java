package userInterface;

import controller.ApplicationController;
import userInterface.panel.PanelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Fenêtre principale de l'application.
 * Refactorisée selon le pattern de UserInterface2 :
 * - Navigation centralisée via changeCurrentPanel() + PanelFactory
 * - WindowAdapter pour la fermeture propre
 * - Container explicite
 */
public class MainWindow extends JFrame {

    private Container frameContainer;
    private MainMenu mainMenu;
    private ApplicationController applicationController;

    public MainWindow() {
        super("Brewery Management");

        // 1. Initialisation du contrôleur
        this.applicationController = new ApplicationController();

        // 2. Configuration de la fenêtre
        this.setBounds(50, 50, 1100, 850);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // géré par WindowAdapter

        // 3. Récupération du container principal
        frameContainer = this.getContentPane();

        // 4. Barre de menu
        mainMenu = new MainMenu(this);
        this.setJMenuBar(mainMenu);

        // 5. Panneau d'accueil par défaut
        changeCurrentPanel("Home", this);

        // 6. Fermeture propre via WindowAdapter (pattern UI2)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);
    }

    /**
     * Change le panneau principal en passant par la PanelFactory.
     * Pattern central repris de UserInterface2.
     */
    public void changeCurrentPanel(String type, MainWindow parent) {
        PanelFactory panelFactory = new PanelFactory();
        frameContainer.removeAll();
        frameContainer.add(panelFactory.createPanel(type, parent));
        frameContainer.revalidate();
        frameContainer.repaint();
        setVisible(true);
    }

    public ApplicationController getApplicationController() {
        return applicationController;
    }
}
