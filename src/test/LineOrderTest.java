package test;

import model.LineOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineOrderTest {

    private LineOrder lineOrder;

    @BeforeEach
    public void setup() {
        lineOrder = new LineOrder(1, "La Rétro", 2, 4.50);
    }

    @Test
    public void testSetQuantity_ValidQuantity() {
        assertDoesNotThrow(() -> {
            lineOrder.setQuantity(5);
        });
        assertEquals(5, lineOrder.getQuantity());
    }

    @Test
    public void testSetQuantity_ZeroQuantity_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lineOrder.setQuantity(0);
        });
        assertEquals("The quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    public void testSetQuantity_NegativeQuantity_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lineOrder.setQuantity(-3);
        });
        assertEquals("The quantity must be greater than zero.", exception.getMessage());
    }
}