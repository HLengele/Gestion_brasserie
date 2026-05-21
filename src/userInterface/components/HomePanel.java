package userInterface.components;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel() {
        // 1. GridBagLayout permet de centrer automatiquement son contenu au milieu
        this.setLayout(new GridBagLayout());

        // Optionnel : Une couleur de fond un peu plus douce que le blanc brut (ex: gris très clair)
        this.setBackground(new Color(245, 245, 245));

        // 2. Création d'un conteneur vertical pour empiler le Welcome et un sous-titre
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Pour qu'on voie la couleur de fond du HomePanel

        // 3. Le message "Welcome" géant
        JLabel lblWelcome = new JLabel("WELCOME");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 52)); // Grosse police en gras
        lblWelcome.setForeground(new Color(44, 62, 80));     // Une jolie couleur bleu ardoise / sombre
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrage Swing

        // 4. Un sous-titre pour habiller l'application
        JLabel lblSubtitle = new JLabel("Système de gestion de la Brasserie");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSubtitle.setForeground(new Color(127, 140, 141));   // Couleur grise plus discrète
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrage Swing

        // 5. Assemblage dans le conteneur avec un espace entre les deux textes
        centerPanel.add(lblWelcome);
        centerPanel.add(Box.createVerticalStrut(15)); // Crée un espace vertical de 15 pixels
        centerPanel.add(lblSubtitle);

        // 6. Ajout du bloc central dans le HomePanel
        this.add(centerPanel);
    }
}