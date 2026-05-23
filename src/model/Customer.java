package model;

import exception.NullValueException;
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
     * @param hour      Heure de la réservation/commande
     * @param table     Table réservée
     * @return          L'objet Order créé conforme à la nouvelle structure
     */
    public Order reserveTable(LocalTime hour, Table table)
            throws NullValueException {
        if (table == null) {
            throw new NullValueException("Impossible de réserver une table nulle");
        }

        // Le constructeur mis à jour d'Order attend uniquement : (orderId, hour, tableNumber)
        // On initialise l'ID à 0 (auto-incrémenté par MySQL)
        return new Order(
                0,
                hour,
                table.getTableNumber()
        );
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}