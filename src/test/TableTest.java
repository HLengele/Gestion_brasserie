package test;

import exception.NullValueException;
import model.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    private Table table;

    @BeforeEach
    public void setup() throws Exception {
        table = new Table(1, 4, "Terrasse");
    }

    @Test
    public void testSetNbPlace_ValidNumber() {
        assertDoesNotThrow(() -> {
            table.setNbPlace(6);
        });
        assertEquals(6, table.getNbPlace());
    }

    @Test
    public void testSetNbPlace_ZeroPlaces_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            table.setNbPlace(0); // 0 place n'est pas autorisé
        });

        assertEquals("The table capacity must be a positive integer", exception.getMessage());
    }

    @Test
    public void testSetNbPlace_NegativePlaces_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            table.setNbPlace(-2);
        });

        assertEquals("The table capacity must be a positive integer", exception.getMessage());
    }
}