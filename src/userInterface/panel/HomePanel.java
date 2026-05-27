package userInterface.panel;

import userInterface.MainWindow;
import userInterface.components.MovingImagePanel;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel(MainWindow parent) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel lblWelcome = new JLabel("Welcome", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Serif", Font.PLAIN, 72));
        lblWelcome.setForeground(new Color(44, 62, 80));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Brewery Management System");
        lblSubtitle.setFont(new Font("Serif", Font.ITALIC, 22));
        lblSubtitle.setForeground(new Color(127, 140, 141));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        MovingImagePanel movingAnimation = new MovingImagePanel();
        movingAnimation.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(lblWelcome);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(lblSubtitle);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(movingAnimation); // L'image va défiler sous le titre
        centerPanel.add(Box.createVerticalGlue());

        this.add(centerPanel, BorderLayout.CENTER);
    }
}