package userInterface.panel;

import userInterface.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau d'accueil.
 * Refactorisé selon le pattern de UserInterface2 :
 * - Accepte MainWindow en paramètre (cohérence avec PanelFactory)
 * - Utilise BorderLayout + CENTER pour le centrage (plus simple que GridBagLayout)
 * - Police Serif (plus élégante qu'Arial)
 */
public class HomePanel extends JPanel {

    public HomePanel(MainWindow parent) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));

        // Conteneur vertical centré
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Titre principal
        JLabel lblWelcome = new JLabel("Welcome", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Serif", Font.PLAIN, 72)); // Serif comme UI2
        lblWelcome.setForeground(new Color(44, 62, 80));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sous-titre
        JLabel lblSubtitle = new JLabel("Brewery Management System");
        lblSubtitle.setFont(new Font("Serif", Font.ITALIC, 22));
        lblSubtitle.setForeground(new Color(127, 140, 141));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(lblWelcome);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(lblSubtitle);
        centerPanel.add(Box.createVerticalGlue());

        this.add(centerPanel, BorderLayout.CENTER);
    }
}
