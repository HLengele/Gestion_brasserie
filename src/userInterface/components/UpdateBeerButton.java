package userInterface.components;

import model.Beer;
import userInterface.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Bouton "Modifier" une bière.
 * Classe dédiée selon le pattern de UserInterface2 (UpdateButton).
 */
public class UpdateBeerButton extends JButton {

    private BeerFormPanel beerForm;
    private MainWindow parent;

    public UpdateBeerButton(BeerFormPanel beerForm, MainWindow parent) {
        super("Modifier");
        this.beerForm = beerForm;
        this.parent = parent;
        this.addActionListener(new PressListener());
    }

    private class PressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Beer beerToUpdate = beerForm.formToBeer();
                if (beerToUpdate.getBeerId() == null || beerToUpdate.getBeerId() == 0) {
                    JOptionPane.showMessageDialog(parent,
                            "Veuillez sélectionner une bière à modifier.",
                            "Attention", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                parent.getApplicationController().updateBeer(beerToUpdate);
                JOptionPane.showMessageDialog(parent, "Bière mise à jour !");
                if (beerForm.getBeerTable() != null) beerForm.getBeerTable().loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Erreur de mise à jour : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
