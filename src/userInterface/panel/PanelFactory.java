package userInterface.panel;

import userInterface.MainWindow;

import javax.swing.*;

public class PanelFactory {

    public JPanel createPanel(String type, MainWindow parent) {
        switch (type) {
            case "Home":
                return new HomePanel(parent);
            case "Beers":
                return new BeerManagementPanel(parent);
            case "Orders":
                return new TakeOrderPanel(parent);
            case "Addition":
                return new AdditionPanel(parent);
            case "SearchReservations":
                return new SearchReservationPanel(parent);
            default:
                return new HomePanel(parent);
        }
    }
}
