package userInterface.components;

import javax.swing.*;
import java.awt.*;

// Implémentation de Runnable (Choix 1 de ton cours sur les Threads)
public class MovingImagePanel extends JPanel implements Runnable {

    private Image image;
    private int imageX = 0;
    private final int imageY = 30;
    private int speedX = 4;
    private final int IMAGE_WIDTH = 100;
    private final int IMAGE_HEIGHT = 100;

    public MovingImagePanel() {
        this.setPreferredSize(new Dimension(800, 160));
        this.setOpaque(false);

        ImageIcon icon = new ImageIcon("src/images/Beer.png");
        this.image = icon.getImage();

        Thread animationThread = new Thread(this);
        animationThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(25);


                imageX += speedX;

                if (imageX > getWidth()) {
                    imageX = -IMAGE_WIDTH;
                }

                repaint();

            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (image != null && image.getWidth(this) > 0) {
            g2d.drawImage(image, imageX, imageY, IMAGE_WIDTH, IMAGE_HEIGHT, this);
        } else {
            g2d.setColor(new Color(230, 126, 34)); // Couleur orange/bière
            g2d.fillRoundRect(imageX, imageY, IMAGE_WIDTH, IMAGE_HEIGHT, 15, 15);

            g2d.setColor(Color.RED);
            g2d.drawString("Image 'src/images/Beer.png' manquante !", imageX, imageY - 5);
        }
    }
}