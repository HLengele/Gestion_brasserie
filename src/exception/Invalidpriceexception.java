package exception;

/**
 * Lancée lorsque le prix d'une bière est invalide (null ou négatif).
 * Utilisée dans Beer.setPrice().
 */
public class Invalidpriceexception extends Exception {

    private Double wrongPrice;

    public Invalidpriceexception(Double wrongPrice, String message) {
        super(message);
        setWrongPrice(wrongPrice);
    }

    public Invalidpriceexception(String message) {
        super(message);
    }

    public Double getWrongPrice() { return wrongPrice; }
    public void setWrongPrice(Double wrongPrice) { this.wrongPrice = wrongPrice; }
}