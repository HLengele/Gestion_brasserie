package exception;

/**
 * Lancée lorsque le nombre de personnes d'une réservation est invalide (null ou ≤ 0).
 * Utilisée dans Reservation.setNbPeople().
 */
public class Invalidnbpeopleexception extends Exception {

    private Integer wrongNbPeople;

    public Invalidnbpeopleexception(Integer wrongNbPeople, String message) {
        super(message);
        setWrongNbPeople(wrongNbPeople);
    }

    public Invalidnbpeopleexception(String message) {
        super(message);
    }

    public Integer getWrongNbPeople() { return wrongNbPeople; }
    public void setWrongNbPeople(Integer wrongNbPeople) { this.wrongNbPeople = wrongNbPeople; }
}