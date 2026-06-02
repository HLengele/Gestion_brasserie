package userInterface.panel;

import userInterface.MainWindow;
import userInterface.components.MovingImagePanel;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel(MainWindow parent) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));

        JPanel centerTextPanel = new JPanel(new GridBagLayout()); // Centre parfaitement son contenu
        centerTextPanel.setOpaque(false);

        JPanel textStack = new JPanel();
        textStack.setLayout(new BoxLayout(textStack, BoxLayout.Y_AXIS));
        textStack.setOpaque(false);

        JLabel lblWelcome = new JLabel("Welcome", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Serif", Font.PLAIN, 72));
        lblWelcome.setForeground(new Color(44, 62, 80));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Brewery Management System");
        lblSubtitle.setFont(new Font("Serif", Font.ITALIC, 24));
        lblSubtitle.setForeground(new Color(127, 140, 141));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        textStack.add(lblWelcome);
        textStack.add(Box.createVerticalStrut(15));
        textStack.add(lblSubtitle);

        centerTextPanel.add(textStack);

        MovingImagePanel movingAnimation = new MovingImagePanel();
        movingAnimation.setPreferredSize(new Dimension(0, 150));

        this.add(centerTextPanel, BorderLayout.CENTER); // Les textes au milieu
        this.add(movingAnimation, BorderLayout.SOUTH);  // L'animation traverse tout le bas !
    }
}