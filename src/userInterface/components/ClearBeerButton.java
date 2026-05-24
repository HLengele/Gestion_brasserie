package userInterface.components;

import userInterface.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Bouton "Vider" le formulaire bière.
 * Équivalent du DefaultButton de UserInterface2.
 */
public class ClearBeerButton extends JButton {

    private BeerFormPanel beerForm;

    public ClearBeerButton(BeerFormPanel beerForm, MainWindow parent) {
        super("Vider");
        this.beerForm = beerForm;
        this.addActionListener(new PressListener());
    }

    private class PressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            beerForm.beerToForm(null);
        }
    }
}
