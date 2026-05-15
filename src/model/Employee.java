package model;

import exception.Nullvalueexception;

import java.util.GregorianCalendar;

public class Employee {

    private Integer idEmployee;
    private String  name;
    private Integer capacite;   // ex : nombre max de tables gérées simultanément

    public Employee(Integer idEmployee, String name, Integer capacite)
            throws Nullvalueexception {
        setIdEmployee(idEmployee);
        setName(name);
        setCapacite(capacite);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Integer idEmployee) { this.idEmployee = idEmployee; }

    public String getName() { return name; }
    public void setName(String name) throws Nullvalueexception {
        if (name != null && !name.isBlank())
            this.name = name;
        else
            throw new Nullvalueexception("Le nom de l'employé ne peut pas être vide");
    }

    public Integer getCapacite() { return capacite; }
    public void setCapacite(Integer capacite) { this.capacite = capacite; }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Crée et retourne une nouvelle commande associée à une table.
     *
     * @param table  La table pour laquelle la commande est créée
     * @return       La commande créée
     */
    public Commande takeOrder(Table table) throws Nullvalueexception {
        return new Commande(null, new GregorianCalendar(), "En cours", this, table);
    }

    /**
     * Collecte (clôture) une commande : marque la commande comme terminée.
     *
     * @param commande  La commande à clôturer
     */
    public void collect(Commande commande) {
        if (commande != null)
            commande.setStatus("Payée");
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return name;
    }
}