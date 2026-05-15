package exception;

/**
 * Lancée lorsqu'une tentative de réservation est faite sur une table
 * déjà occupée pour le créneau demandé.
 * Utilisée dans la couche Business lors de la validation de la disponibilité.
 */
public class Tablenotavailableexception extends Exception {

    private Integer tableId;
    private String  requestedSlot;   // ex : "2025-07-14 19:30"

    public Tablenotavailableexception(Integer tableId, String requestedSlot, String message) {
        super(message);
        setTableId(tableId);
        setRequestedSlot(requestedSlot);
    }

    public Tablenotavailableexception(String message) {
        super(message);
    }

    public Integer getTableId() { return tableId; }
    public void setTableId(Integer tableId) { this.tableId = tableId; }

    public String getRequestedSlot() { return requestedSlot; }
    public void setRequestedSlot(String requestedSlot) { this.requestedSlot = requestedSlot; }
}