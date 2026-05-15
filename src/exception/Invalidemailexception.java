package exception;

/**
 * Lancée lorsque l'adresse e-mail fournie est invalide (format incorrect ou vide).
 * Utilisée dans Customer.setEmail().
 */
public class Invalidemailexception extends Exception {

  private String wrongEmail;

  public Invalidemailexception(String wrongEmail, String message) {
    super(message);
    setWrongEmail(wrongEmail);
  }

  public Invalidemailexception(String message) {
    super(message);
  }

  public String getWrongEmail() { return wrongEmail; }
  public void setWrongEmail(String wrongEmail) { this.wrongEmail = wrongEmail; }
}