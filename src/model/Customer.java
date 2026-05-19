package model;

import exception.NullValueException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Customer {

    private int customerId;
    private String email;
    private String phone;
    private String name;

    public Customer(int customerId, String email, String phone, String name)
            throws NullValueException {
        setCustomerId(customerId);
        setEmail(email);
        setPhone(phone);
        setName(name);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) throws NullValueException {
        if (customerId >= 0) {
            this.customerId = customerId;
        } else {
            throw new NullValueException("L'identifiant du client ne peut pas être négatif");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws NullValueException {
        if (email != null && !email.isBlank()) {
            this.email = email;
        } else {
            throw new NullValueException("L'email du client ne peut pas être vide");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NullValueException {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new NullValueException("Le nom du client ne peut pas être vide");
        }
    }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Crée une commande (faisant office de réservation) pour ce client.
     *
     * @param date      Date de la réservation/commande
     * @param hour      Heure de la réservation/commande
     * @param table     Table réservée
     * @return          L'objet Order créé
     */
    public Order reserveTable(LocalDate date, LocalTime hour, Table table)
            throws NullValueException {
        if (table == null) {
            throw new NullValueException("Impossible de réserver une table nulle");
        }

        // Le constructeur d'Order attend : (orderId, orderDate, hour, status, tableNumber, employeeId)
        // On initialise l'ID à 0 (géré par la BDD), le statut à "Réservé",
        // et l'employeeId à 0 (car aucun employé n'a encore pris en charge physiquement la table).
        return new Order(
                0,
                date,
                hour,
                "Réservé",
                table.getTableNumber(),
                0
        );
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}