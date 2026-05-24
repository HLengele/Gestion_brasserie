package userInterface.components;

import userInterface.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Bouton "Supprimer" une bière.
 * Classe dédiée selon le pattern de UserInterface2 (DeleteButton).
 * Demande une confirmation avant suppression.
 */
public class DeleteBeerButton extends JButton {

    private BeerFormPanel beerForm;
    private MainWindow parent;

    public DeleteBeerButton(BeerFormPanel beerForm, MainWindow parent) {
        super("Supprimer");
        this.beerForm = beerForm;
        this.parent = parent;
        this.addActionListener(new PressListener());
    }

    private class PressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer beerId = beerForm.getCurrentBeerId();

            if (beerId == null) {
                JOptionPane.showMessageDialog(parent,
                        "Vous devez d'abord sélectionner une bière !",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(parent,
                    "Voulez-vous vraiment supprimer la bière #" + beerId + " ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    parent.getApplicationController().deleteBeer(beerId);
                    if (beerForm.getBeerTable() != null) beerForm.getBeerTable().loadTable();
                    beerForm.setCurrentBeerId(null);
                    beerForm.beerToForm(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent,
                            "Erreur lors de la suppression : " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
