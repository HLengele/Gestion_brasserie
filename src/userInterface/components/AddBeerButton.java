package userInterface.components;

import model.Beer;
import userInterface.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Bouton "Ajouter" une bière.
 * Classe dédiée selon le pattern de UserInterface2 (SubmitButton).
 */
public class AddBeerButton extends JButton {

    private BeerFormPanel beerForm;
    private MainWindow parent;

    public AddBeerButton(BeerFormPanel beerForm, MainWindow parent) {
        super("Ajouter");
        this.beerForm = beerForm;
        this.parent = parent;
        this.addActionListener(new PressListener());
    }

    private class PressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                beerForm.setCurrentBeerId(null);
                Beer newBeer = beerForm.formToBeer();
                parent.getApplicationController().addBeer(newBeer);
                JOptionPane.showMessageDialog(parent, "Bière ajoutée avec succès !");
                if (beerForm.getBeerTable() != null) beerForm.getBeerTable().loadTable();
                beerForm.beerToForm(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Erreur lors de l'ajout : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
