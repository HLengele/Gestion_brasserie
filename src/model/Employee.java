package model;

import exception.NullValueException;
import java.time.LocalDate;
import java.util.GregorianCalendar;

public class Employee {

    // ── Attributs du diagramme ─────────────────────────────────────────────────
    private Integer employeeId;
    private String lastName;
    private String firstName;
    private LocalDate hiringDate;

    // ── Relations (issues du diagramme) ────────────────────────────────────────
    private City city;

    // ── Attributs spécifiques à votre logique (hors diagramme) ─────────────────
    private Integer capacite; // ex : nombre max de tables gérées simultanément

    // ── Constructeur ───────────────────────────────────────────────────────────
    public Employee(Integer employeeId, String firstName, String lastName, LocalDate hiringDate, City city, Integer capacite)
            throws NullValueException {
        setEmployeeId(employeeId);
        setFirstName(firstName);
        setLastName(lastName);
        setHiringDate(hiringDate);
        setCity(city);
        setCapacite(capacite);
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

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Crée et retourne une nouvelle commande associée à une table.
     *
     * @param table  La table pour laquelle la commande est créée
     * @return       La commande créée
     */
    public Order takeOrder(Table table) throws NullValueException {
        if (table == null) {
            throw new NullValueException("Impossible de prendre une commande pour une table nulle");
        }

        // Initialisation automatique avec l'ID 0 (ou géré par l'AUTO_INCREMENT de la BDD lors de l'insertion)
        // l'identifiant de l'employé courant (this.getEmployeeId()) et le numéro de la table
        return new Order(
                0,
                java.time.LocalDate.now(),
                java.time.LocalTime.now(),
                "En cours",
                table.getTableNumber(), // Assurez-vous que le getter de la classe Table s'appelle bien ainsi
                this.getEmployeeId()
        );
    }

    /**
     * Collecte (clôture) une commande : marque la commande comme terminée.
     *
     * @param order  La commande à clôturer
     */
    public void collect(Order order) throws NullValueException {
        if (order != null) {
            order.setStatus("Payée");
        }
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        // Correction : "name" n'existait pas, on concatène le prénom et le nom
        return firstName + " " + lastName;
    }
}