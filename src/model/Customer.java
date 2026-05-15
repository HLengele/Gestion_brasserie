package model;

import exception.Nullvalueexception;

import java.util.GregorianCalendar;

public class Customer {

    private Integer customerId;
    private String email;
    private String phone;
    private String name;

    public Customer(Integer customerId, String email, String phone, String name)
            throws Nullvalueexception {
        setCustomerId(customerId);
        setEmail(email);
        setPhone(phone);
        setName(name);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getEmail() { return email; }
    public void setEmail(String email) throws Nullvalueexception {
        if (email != null && !email.isBlank())
            this.email = email;
        else
            throw new Nullvalueexception("L'email du client ne peut pas être vide");
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getName() { return name; }
    public void setName(String name) throws Nullvalueexception {
        if (name != null && !name.isBlank())
            this.name = name;
        else
            throw new Nullvalueexception("Le nom du client ne peut pas être vide");
    }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Crée une réservation pour ce client.
     *
     * @param date      Date de la réservation
     * @param hour      Heure de la réservation (ex : "19:30")
     * @param nbPeople  Nombre de personnes
     * @param table     Table réservée
     * @return          La réservation créée
     */
    public Reservation reserveTable(GregorianCalendar date, String hour,
                                    Integer nbPeople, Table table)
            throws Nullvalueexception {
        return new Reservation(null, nbPeople, hour, date, this, table);
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}