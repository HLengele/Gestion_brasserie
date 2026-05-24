package userInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Barre de menu principale.
 * Refactorisée selon le pattern de UserInterface2 :
 * - Menus organisés en catégories ("Application", "Gestion")
 * - Raccourcis clavier (Ctrl+H, Ctrl+B, Ctrl+O, Ctrl+A, Ctrl+Q)
 * - Item "Quitter" avec ExitListener dédié
 * - Mnémoniques pour accessibilité clavier
 */
public class MainMenu extends JMenuBar {

    private MainWindow parent;

    // Menus
    private JMenu appMenu;
    private JMenu gestionMenu;

    // Items Application
    private JMenuItem itemHome;
    private JMenuItem itemExit;

    // Items Gestion
    private JMenuItem itemBeers;
    private JMenuItem itemOrders;
    private JMenuItem itemAddition;
    private JMenuItem itemSearchReservations;

    public MainMenu(MainWindow parent) {
        this.parent = parent;

        // --- Menu "Application" (pattern UI2 : menu "Application" avec Home + Exit) ---
        appMenu = new JMenu("Application");
        appMenu.setMnemonic('A');
        this.add(appMenu);

        itemHome = new JMenuItem("🏠 Accueil");
        itemHome.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        itemHome.addActionListener(new ChangePanelListener("Home"));
        appMenu.add(itemHome);

        appMenu.addSeparator();

        itemExit = new JMenuItem("Quitter");
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        itemExit.addActionListener(new ExitListener());
        appMenu.add(itemExit);

        // --- Menu "Gestion" (pattern UI2 : menu "Table" avec sous-items) ---
        gestionMenu = new JMenu("Gestion");
        gestionMenu.setMnemonic('G');
        this.add(gestionMenu);

        itemBeers = new JMenuItem("🍺 Bières");
        itemBeers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
        itemBeers.addActionListener(new ChangePanelListener("Beers"));
        gestionMenu.add(itemBeers);

        itemOrders = new JMenuItem("📝 Commandes");
        itemOrders.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        itemOrders.addActionListener(new ChangePanelListener("Orders"));
        gestionMenu.add(itemOrders);

        itemAddition = new JMenuItem("🧾 Addition");
        itemAddition.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        itemAddition.addActionListener(new ChangePanelListener("Addition"));
        gestionMenu.add(itemAddition);

        itemSearchReservations = new JMenuItem("🔍 Recherche Réservations");
        itemSearchReservations.addActionListener(new ChangePanelListener("SearchReservations"));
        gestionMenu.addSeparator(); // Pour faire plus propre
        gestionMenu.add(itemSearchReservations);
    }

    // --- Listeners internes (pattern UI2 : classes internes nommées) ---

    private class ChangePanelListener implements ActionListener {
        private String type;

        public ChangePanelListener(String type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.changeCurrentPanel(this.type, parent);
        }
    }

    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
