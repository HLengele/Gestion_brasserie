package test;

import business.BeerManager;
import model.Beer;
import model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeerManagerTest {

    private BeerManager beerManager;

    @BeforeEach
    public void setup() {
        beerManager = new BeerManager(null);
    }

    @Test
    public void testAddBeer_NullBeer_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            beerManager.addBeer(null);
        });

        assertEquals("The beer to add cannot be null.", exception.getMessage());
    }

    @Test
    public void testAddBeer_BeerWithExistingId_ThrowsException() throws Exception {
        Category cat = new Category(1, "Test");
        Beer beerWithId = new Beer(99, "Bière Test", "Blonde", 5.0, null, true, LocalDate.now(), null, cat);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            beerManager.addBeer(beerWithId);
        });

        assertEquals("A new beer must not have an ID before insertion.", exception.getMessage());
    }
}