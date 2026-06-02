package test;

import exception.NullValueException;
import model.Beer;
import model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BeerTest {

    private Beer beer;
    private Category category;

    @BeforeEach
    public void setup() throws Exception {
        category = new Category(1, "Blonde");
        beer = new Beer(1, "La Rétro", "Blonde", 4.50, "Bière de test", true, LocalDate.now().minusDays(1), "Comment", category);
    }


    @Test
    public void testSetName_ValidName() {
        assertDoesNotThrow(() -> {
            beer.setName("Nouvelle Blonde");
        });

        assertEquals("Nouvelle Blonde", beer.getName());
    }


    @Test
    public void testSetName_EmptyName_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            beer.setName("");
        });

        assertEquals("The beer name is required.", exception.getMessage());
    }

    @Test
    public void testSetPrice_NegativePrice_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            beer.setPrice(-2.50);
        });

        assertEquals("The price must be positive.", exception.getMessage());
    }

    @Test
    public void testSetMarketLaunchDate_FutureDate_ThrowsException() {
        LocalDate futureDate = LocalDate.now().plusDays(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            beer.setMarketLaunchDate(futureDate);
        });

        assertEquals("The market launch date cannot be in the future.", exception.getMessage());
    }
}