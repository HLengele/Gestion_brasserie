package exception;

/**
 * Lancée lorsque la capacité fournie est invalide (valeur nulle ou ≤ 0).
 * Utilisée dans Table.setCapacite() et Employee.setCapacite().
 */
public class Invalidcapaciteexception extends Exception {

    private Integer wrongCapacite;

    public Invalidcapaciteexception(Integer wrongCapacite, String message) {
        super(message);
        setWrongCapacite(wrongCapacite);
    }

    public Invalidcapaciteexception(String message) {
        super(message);
    }

    public Integer getWrongCapacite() { return wrongCapacite; }
    public void setWrongCapacite(Integer wrongCapacite) { this.wrongCapacite = wrongCapacite; }
}