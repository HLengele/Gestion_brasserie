package userInterface;

import controller.IApplicationController; // N'oublie pas cet import !
import userInterface.panel.PanelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private Container frameContainer;
    private MainMenu mainMenu;
    private IApplicationController applicationController;

    public MainWindow(IApplicationController controller) {
        super("Brewery Management");

        this.applicationController = controller;

        this.setBounds(50, 50, 1100, 850);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frameContainer = this.getContentPane();

        mainMenu = new MainMenu(this);
        this.setJMenuBar(mainMenu);

        changeCurrentPanel("Home", this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);
    }

    public void changeCurrentPanel(String type, MainWindow parent) {
        PanelFactory panelFactory = new PanelFactory();
        frameContainer.removeAll();
        frameContainer.add(panelFactory.createPanel(type, parent));
        frameContainer.revalidate();
        frameContainer.repaint();
        setVisible(true);
    }

    public IApplicationController getApplicationController() {
        return applicationController;
    }
}