package model;

import exception.Nullvalueexception;

public class CommandLine {

    private Integer id;
    private Integer quantite;
    private Beer    beer;
    private Integer commandeId;   // clé étrangère vers Commande

    public CommandLine(Integer id, Integer quantite, Beer beer, Integer commandeId)
            throws Nullvalueexception {
        setId(id);
        setQuantite(quantite);
        setBeer(beer);
        setCommandeId(commandeId);
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) throws Nullvalueexception {
        if (quantite != null && quantite > 0)
            this.quantite = quantite;
        else
            throw new Nullvalueexception("La quantité doit être strictement positive");
    }

    public Beer getBeer() { return beer; }
    public void setBeer(Beer beer) throws Nullvalueexception {
        if (beer != null)
            this.beer = beer;
        else
            throw new Nullvalueexception("La bière ne peut pas être nulle");
    }

    public Integer getCommandeId() { return commandeId; }
    public void setCommandeId(Integer commandeId) { this.commandeId = commandeId; }

    // ── Méthodes métier ────────────────────────────────────────────────────────

    /**
     * Calcule le sous-total de cette ligne : quantité × prix unitaire de la bière.
     *
     * @return Sous-total en euros
     */
    public double calculerSousTotal() {
        if (quantite != null && beer != null && beer.getPrice() != null)
            return quantite * beer.getPrice();
        return 0.0;
    }

    // ── toString ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return beer.getNom() + " x" + quantite + " = " + calculerSousTotal() + " €";
    }
}