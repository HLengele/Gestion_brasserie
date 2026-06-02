package test;

import model.City;
import model.Customer;
import model.Reservation;
import model.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    public void setup() throws Exception {
        City city = new City(1, "Wasseiges", "4219");
        Customer customer = new Customer(1, "test@test.com", "0470", "Test", city);
        Table table = new Table(1, 4, "Terrasse");

        reservation = new Reservation(1, LocalDate.now(), 2, customer, table);
    }

    @Test
    public void testSetNbPeople_ValidNumber() {
        assertDoesNotThrow(() -> {
            reservation.setNbPeople(4);
        });
        assertEquals(4, reservation.getNbPeople());
    }

    @Test
    public void testSetNbPeople_ZeroPeople_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservation.setNbPeople(0);
        });
        assertEquals("A reservation must be for at least 1 person.", exception.getMessage());
    }
}