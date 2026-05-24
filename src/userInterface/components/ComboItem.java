package userInterface.components;

/**
 * Utilitaire pour les JComboBox : associe un libellé affiché à une valeur entière.
 * Classe reprise de UserInterface2 pour une gestion propre des combos (bières, tables...).
 */
public class ComboItem {
    private String key;
    private Integer value;

    public ComboItem(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
