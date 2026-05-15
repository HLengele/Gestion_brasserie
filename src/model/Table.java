package model;

import exception.Nullvalueexception;

import java.util.GregorianCalendar;

public class Table {

    private Integer idTable;
    private Integer numero;
    private Integer capacite;

    public Table(Integer idTable, Integer numero, Integer capacite)
            throws Nullvalueexception {
        setIdTable(idTable);
        setNumero(numero);
        setCapacite(capacite);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getIdTable() { return idTable; }
    public void setIdTable(Integer idTable) { this.idTable = idTable; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) throws Nullvalueexception {
        if (numero != null && numero > 0)
            this.numero = numero;
        else
            throw new Nullvalueexception("Le numéro de table doit être positif");
    }

    public Integer getCapacite() { return capacite; }
    public void setCapacite(Integer capacite) throws Nullvalueexception {
        if (capacite != null && capacite > 0)
            this.capacite = capacite;
        else
            throw new Nullvalueexception("La capacité de la table doit être positive");
    }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Vérifie si la table est disponible pour une date et une heure données.
     * La logique réelle doit interroger la couche Data Access via le manager.
     * Cette méthode est un point d'entrée côté modèle.
     *
     * @param date  Date souhaitée
     * @param hour  Heure souhaitée
     * @return      true si disponible, false sinon
     */
    public boolean isAvailable(GregorianCalendar date, String hour) {
        // Implémentation concrète déléguée à la couche Business / DataAccess
        return true;
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Table n°" + numero + " (capacité : " + capacite + ")";
    }
}