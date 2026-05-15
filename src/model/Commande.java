package model;

import exception.Nullvalueexception;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Commande {

    private Integer commandId;
    private GregorianCalendar commandDate;
    private String  status;
    private Employee employee;
    private Table   table;

    private ArrayList<CommandLine> lignes;

    public Commande(Integer commandId, GregorianCalendar commandDate,
                    String status, Employee employee, Table table)
            throws Nullvalueexception {
        setCommandId(commandId);
        setCommandDate(commandDate);
        setStatus(status);
        setEmployee(employee);
        setTable(table);
        this.lignes = new ArrayList<>();
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getCommandId() { return commandId; }
    public void setCommandId(Integer commandId) { this.commandId = commandId; }

    public GregorianCalendar getCommandDate() { return commandDate; }
    public void setCommandDate(GregorianCalendar commandDate) throws Nullvalueexception {
        if (commandDate != null)
            this.commandDate = commandDate;
        else
            throw new Nullvalueexception("La date de commande ne peut pas être nulle");
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) throws Nullvalueexception {
        if (employee != null)
            this.employee = employee;
        else
            throw new Nullvalueexception("L'employé ne peut pas être nul");
    }

    public Table getTable() { return table; }
    public void setTable(Table table) throws Nullvalueexception {
        if (table != null)
            this.table = table;
        else
            throw new Nullvalueexception("La table ne peut pas être nulle");
    }

    public ArrayList<CommandLine> getLignes() { return lignes; }
    public void setLignes(ArrayList<CommandLine> lignes) { this.lignes = lignes; }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Ajoute un produit (bière) à la commande avec la quantité souhaitée.
     * Si la bière est déjà présente dans la commande, la quantité est additionnée.
     *
     * @param beer      La bière à ajouter
     * @param quantite  La quantité commandée
     */
    public void ajouterProduit(Beer beer, int quantite) throws Nullvalueexception {
        // Recherche d'une ligne existante pour cette bière
        for (CommandLine ligne : lignes) {
            if (ligne.getBeer().getBeerId().equals(beer.getBeerId())) {
                ligne.setQuantite(ligne.getQuantite() + quantite);
                return;
            }
        }
        // Nouvelle ligne
        lignes.add(new CommandLine(null, quantite, beer, this.commandId));
    }

    /**
     * Calcule le total de la commande en sommant les sous-totaux de chaque ligne.
     *
     * @return Total en euros
     */
    public double calculerTotal() {
        double total = 0.0;
        for (CommandLine ligne : lignes)
            total += ligne.calculerSousTotal();
        return total;
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Commande #" + commandId
                + " – " + table
                + " – " + status
                + " – Total : " + calculerTotal() + " €";
    }
}