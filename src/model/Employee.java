package model;

import exception.NullValueException;
import java.time.LocalDate;

public class Employee {

    // ── Attributs du diagramme ─────────────────────────────────────────────────
    private Integer employeeId;
    private String lastName;
    private String firstName;
    private LocalDate hiringDate;

    // ── Relations (issues du diagramme) ────────────────────────────────────────
    private City city;

    // ── Constructeur ───────────────────────────────────────────────────────────
    public Employee(Integer employeeId, String firstName, String lastName, LocalDate hiringDate, City city)
            throws NullValueException {
        setEmployeeId(employeeId);
        setFirstName(firstName);
        setLastName(lastName);
        setHiringDate(hiringDate);
        setCity(city);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws NullValueException {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        } else {
            throw new NullValueException("Le prénom de l'employé ne peut pas être vide");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws NullValueException {
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        } else {
            throw new NullValueException("Le nom de l'employé ne peut pas être vide");
        }
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Crée et retourne une nouvelle commande associée à une table.
     * Adapté au nouveau schéma BD (orderId, hour, tableNumber).
     *
     * @param table  La table pour laquelle la commande est créée
     * @return       La commande créée
     */
    public Order takeOrder(Table table) throws NullValueException {
        if (table == null) {
            throw new NullValueException("Impossible de prendre une commande pour une table nulle");
        }

        // Initialisation automatique avec l'ID 0 (géré par l'AUTO_INCREMENT de la BDD)
        // et uniquement avec l'heure et le numéro de table (conformément à la BDD)
        return new Order(
                0,
                java.time.LocalTime.now(),
                table.getTableNumber()
        );
    }

    /**
     * Collecte (clôture) une commande.
     * ATTENTION : Le champ "status" n'existant plus en BDD, cette méthode
     * devra utiliser une autre logique si vous souhaitez conserver un historique.
     *
     * @param order  La commande à clôturer
     */
    public void collect(Order order) throws NullValueException {
        if (order != null) {
            // L'instruction order.setStatus("Payée") a été retirée car elle est incompatible avec la BD.
            // À l'avenir, vous pourriez par exemple supprimer la commande ou l'archiver dans une autre table.
        }
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}