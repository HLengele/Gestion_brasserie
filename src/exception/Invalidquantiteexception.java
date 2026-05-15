package exception;

/**
 * Lancée lorsque la quantité d'une ligne de commande est invalide (null ou ≤ 0).
 * Utilisée dans CommandLine.setQuantite() et Commande.ajouterProduit().
 */
public class Invalidquantiteexception extends Exception {

    private Integer wrongQuantite;

    public Invalidquantiteexception(Integer wrongQuantite, String message) {
        super(message);
        setWrongQuantite(wrongQuantite);
    }

    public Invalidquantiteexception(String message) {
        super(message);
    }

    public Integer getWrongQuantite() { return wrongQuantite; }
    public void setWrongQuantite(Integer wrongQuantite) { this.wrongQuantite = wrongQuantite; }
}