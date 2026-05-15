package model;

import exception.Nullvalueexception;

import java.util.GregorianCalendar;

public class Reservation {

    private Integer reservationId;
    private Integer nbPeople;
    private String  hour;
    private GregorianCalendar date;
    private Customer customer;
    private Table table;

    public Reservation(Integer reservationId, Integer nbPeople, String hour,
                       GregorianCalendar date, Customer customer, Table table)
            throws Nullvalueexception {
        setReservationId(reservationId);
        setNbPeople(nbPeople);
        setHour(hour);
        setDate(date);
        setCustomer(customer);
        setTable(table);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getReservationId() { return reservationId; }
    public void setReservationId(Integer reservationId) { this.reservationId = reservationId; }

    public Integer getNbPeople() { return nbPeople; }
    public void setNbPeople(Integer nbPeople) throws Nullvalueexception {
        if (nbPeople != null && nbPeople > 0)
            this.nbPeople = nbPeople;
        else
            throw new Nullvalueexception("Le nombre de personnes doit être positif");
    }

    public String getHour() { return hour; }
    public void setHour(String hour) throws Nullvalueexception {
        if (hour != null && !hour.isBlank())
            this.hour = hour;
        else
            throw new Nullvalueexception("L'heure de réservation ne peut pas être vide");
    }

    public GregorianCalendar getDate() { return date; }
    public void setDate(GregorianCalendar date) throws Nullvalueexception {
        if (date != null)
            this.date = date;
        else
            throw new Nullvalueexception("La date de réservation ne peut pas être nulle");
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) throws Nullvalueexception {
        if (customer != null)
            this.customer = customer;
        else
            throw new Nullvalueexception("Le client ne peut pas être nul");
    }

    public Table getTable() { return table; }
    public void setTable(Table table) throws Nullvalueexception {
        if (table != null)
            this.table = table;
        else
            throw new Nullvalueexception("La table ne peut pas être nulle");
    }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Confirme la réservation (par exemple : envoi d'un e-mail, mise à jour du statut…).
     * L'implémentation concrète est déléguée à la couche Business.
     */
    public void confirm() {
        // délégué au manager
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Réservation #" + reservationId
                + " – " + customer.getName()
                + " – " + table
                + " – " + nbPeople + " pers. le " + hour;
    }
}