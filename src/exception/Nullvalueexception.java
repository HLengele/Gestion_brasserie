package exception;

/**
 * Lancée lorsqu'une valeur obligatoire est nulle ou vide.
 * Utilisée dans les setters de toutes les classes modèle.
 */
public class Nullvalueexception extends Exception {

    private String fieldName;

    public Nullvalueexception(String message) {
        super(message);
    }

    public Nullvalueexception(String fieldName, String message) {
        super(message);
        setFieldName(fieldName);
    }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
}