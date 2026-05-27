package userInterface.components;

import model.Beer;
import userInterface.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddBeerButton extends JButton {

    private BeerFormPanel beerForm;
    private MainWindow parent;

    public AddBeerButton(BeerFormPanel beerForm, MainWindow parent) {
        super("Add");
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
                JOptionPane.showMessageDialog(parent, "Beer added successfully!");
                if (beerForm.getBeerTable() != null) beerForm.getBeerTable().loadTable();
                beerForm.beerToForm(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Error while adding: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
