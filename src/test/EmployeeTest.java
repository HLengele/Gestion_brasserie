package test;

import exception.NullValueException;
import model.City;
import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    private Employee employee;
    private City city;

    @BeforeEach
    public void setup() throws Exception {
        city = new City(2, "Namur", "5000");
        employee = new Employee(1, "Laurent", "Dubois", LocalDate.now().minusYears(1), city);
    }

    @Test
    public void testSetFirstName_EmptyName_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            employee.setFirstName("   "); // Des espaces vides
        });
        assertEquals("The employee's first name cannot be empty", exception.getMessage());
    }

    @Test
    public void testSetLastName_NullName_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            employee.setLastName(null);
        });
        assertEquals("The employee's last name cannot be empty", exception.getMessage());
    }

    @Test
    public void testTakeOrder_NullTable_ThrowsException() {
        NullValueException exception = assertThrows(NullValueException.class, () -> {
            employee.takeOrder(null);
        });
        assertEquals("Cannot take an order for a null table", exception.getMessage());
    }
}