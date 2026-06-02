package test;

import exception.NullValueException;
import model.City;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private City city;

    @BeforeEach
    public void setup() throws Exception {
        city = new City(1, "Wasseiges", "4219");
        customer = new Customer(1, "alice@email.com", "0470112233", "Alice", city);
    }

    @Test
    public void testSetName_EmptyName_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            customer.setName("");
        });
        assertEquals("The customer's name cannot be empty", exception.getMessage());
    }

    @Test
    public void testSetEmail_NullEmail_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            customer.setEmail(null);
        });
        assertEquals("The customer's email cannot be empty", exception.getMessage());
    }

    @Test
    public void testSetCustomerId_NegativeId_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            customer.setCustomerId(-5);
        });
        assertEquals("The customer ID cannot be negative", exception.getMessage());
    }

    @Test
    public void testReserveTable_NullTable_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            customer.reserveTable(LocalTime.now(), null);
        });
        assertEquals("Cannot reserve a null table", exception.getMessage());
    }
}