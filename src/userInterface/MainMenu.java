package userInterface;

import userInterface.MainWindow;
import userInterface.panel.BeerManagementPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JMenuBar {

    private MainWindow mainWindow;

    public MainMenu(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        // 1. Création du menu principal "Gestion"
        JMenu menuGestion = new JMenu("Gestion");
        // Vous pouvez ajouter d'autres menus ici (ex: JMenu menuFichier = new JMenu("Fichier");)

        // 2. Création de l'option pour gérer les bières
        JMenuItem biereItem = new JMenuItem("Gérer les Bières");

        biereItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // On crée une nouvelle instance du panneau de gestion des bières
                BeerManagementPanel beerPanel = new BeerManagementPanel(mainWindow);

                // On utilise la bonne méthode : setMainPanel (et non changePanel)
                mainWindow.setMainPanel(beerPanel);
            }
        });

        // 4. Assemblage du menu
        menuGestion.add(biereItem);

        // Si vous avez d'autres fonctionnalités (ex: Employés), ajoutez-les à la suite :
        // JMenuItem employeItem = new JMenuItem("Gérer les Employés");
        // menuGestion.add(employeItem);

        // 5. Ajout du menu complet à la barre de menu
        this.add(menuGestion);
    }
}