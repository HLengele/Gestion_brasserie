package model;

import exception.NullValueException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Order {

    // ── Attributs (Types primitifs) ───────────────────────────────────────────
    private int orderId;
    private LocalDate orderDate;
    private LocalTime hour;
    private String status;

    // ── Clés étrangères / Relations ────────────────────────────────────────────
    private int tableNumber;
    private int employeeId;

    // ── Constructeur Complet ───────────────────────────────────────────────────
    public Order(int orderId, LocalDate orderDate, LocalTime hour, String status, int tableNumber, int employeeId)
            throws NullValueException {
        setOrderId(orderId);
        setOrderDate(orderDate);
        setHour(hour);
        setStatus(status);
        setTableNumber(tableNumber);
        setEmployeeId(employeeId);
    }

    // ── Getters et Setters ─────────────────────────────────────────────────────

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) throws NullValueException {
        if (orderId >= 0) {
            this.orderId = orderId;
        } else {
            throw new NullValueException("L'identifiant de la commande ne peut pas être négatif");
        }
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) throws NullValueException {
        if (orderDate != null) {
            this.orderDate = orderDate;
        } else {
            throw new NullValueException("La date de la commande ne peut pas être nulle");
        }
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) throws NullValueException {
        if (hour != null) {
            this.hour = hour;
        } else {
            throw new NullValueException("L'heure de la commande ne peut pas être nulle");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) throws NullValueException {
        if (status != null && !status.isBlank()) {
            this.status = status;
        } else {
            throw new NullValueException("Le statut de la commande ne peut pas être vide");
        }
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) throws NullValueException {
        if (tableNumber > 0) {
            this.tableNumber = tableNumber;
        } else {
            throw new NullValueException("Le numéro de table doit être supérieur à 0");
        }
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) throws NullValueException {
        if (employeeId >= 0) {
            this.employeeId = employeeId;
        } else {
            throw new NullValueException("L'identifiant de l'employé ne peut pas être négatif");
        }
    }

    // ── Méthode Métier ─────────────────────────────────────────────────────────

    public double calculerTotal() {
        return 0.0;
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Commande n°" + orderId + " [Statut: " + status + "] - Table " + tableNumber + " le " + orderDate + " à " + hour;
    }
}