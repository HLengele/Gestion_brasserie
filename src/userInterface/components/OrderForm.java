package userInterface.components;

import exception.NullValueException;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OrderForm extends JPanel {

    private JTextField txtOrderId;
    private JTextField txtHour;
    private JSpinner   spinnerTableNumber;

    private Integer currentOrderId = null; // Permet de savoir si on édite ou si on crée

    public OrderForm() {
        // Disposition en grille : 3 lignes, 2 colonnes, espacement de 10px
        this.setLayout(new GridLayout(3, 2, 10, 10));

        // 1. Initialisation des composants
        txtOrderId = new JTextField();
        txtOrderId.setEditable(false); // L'ID est géré par la BD, donc non modifiable
        txtOrderId.setBackground(Color.LIGHT_GRAY);

        txtHour = new JTextField();
        txtHour.setToolTipText("Format HH:mm:ss (ex: 14:30:00)");

        // Spinner pour le numéro de table (de 1 à 100, pas par pas de 1)
        spinnerTableNumber = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        // 2. Ajout des composants au panneau avec leurs labels
        this.add(new JLabel("ID Commande (Auto) :"));
        this.add(txtOrderId);

        this.add(new JLabel("Heures (HH:mm:ss) :"));
        this.add(txtHour);

        this.add(new JLabel("Numéro de Table :"));
        this.add(spinnerTableNumber);

        // Par défaut, on initialise le formulaire à vide
        orderToForm(null);
    }

    /**
     * Remplit le formulaire avec les données d'une commande.
     * Si le paramètre est 'null', le formulaire est vidé (mode création).
     */
    public void orderToForm(Order order) {
        if (order == null) {
            currentOrderId = null;
            txtOrderId.setText("");
            // On pré-remplit automatiquement avec l'heure actuelle pour aider l'utilisateur
            txtHour.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            spinnerTableNumber.setValue(1);
        } else {
            currentOrderId = order.getOrderId();
            txtOrderId.setText(String.valueOf(order.getOrderId()));
            txtHour.setText(order.getHour().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            spinnerTableNumber.setValue(order.getTableNumber());
        }
    }

    /**
     * Récupère les données saisies dans le formulaire pour créer/mettre à jour un objet Order.
     */
    public Order formToOrder() throws NullValueException, IllegalArgumentException {
        // Validation du format de l'heure
        LocalTime hour;
        try {
            hour = LocalTime.parse(txtHour.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le format de l'heure est incorrect (HH:mm:ss requis).");
        }

        int tableNumber = (int) spinnerTableNumber.getValue();

        // Si currentOrderId est null, c'est une nouvelle commande (on met l'ID à 0)
        int id = (currentOrderId == null) ? 0 : currentOrderId;

        // Instanciation de votre modèle Order mis à jour (3 paramètres)
        return new Order(id, hour, tableNumber);
    }

    // Getter et Setter pour l'ID de la commande en cours
    public Integer getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(Integer currentOrderId) {
        this.currentOrderId = currentOrderId;
    }
}