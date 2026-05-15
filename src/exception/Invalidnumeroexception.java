package exception;

/**
 * Lancée lorsque le numéro de table est invalide (null ou ≤ 0).
 * Utilisée dans Table.setNumero().
 */
public class Invalidnumeroexception extends Exception {

    private Integer wrongNumero;

    public Invalidnumeroexception(Integer wrongNumero, String message) {
        super(message);
        setWrongNumero(wrongNumero);
    }

    public Invalidnumeroexception(String message) {
        super(message);
    }

    public Integer getWrongNumero() { return wrongNumero; }
    public void setWrongNumero(Integer wrongNumero) { this.wrongNumero = wrongNumero; }
}