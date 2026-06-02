package test;

import business.OrderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderManagerTest {

    private OrderManager orderManager;

    @BeforeEach
    public void setup() {
        // ARRANGE : On instancie le Manager.
        // On passe 'null' pour les DAO (orderDao, cityDao, etc.) car on veut tester
        // uniquement la sécurité sur le numéro de table SANS toucher à la base de données.
        orderManager = new OrderManager(null,null, null, null, null);
    }

    // --- TESTS D'ÉCHECS (Validation des exceptions) ---

    @Test
    public void testCalculateTableAddition_ZeroTableNumber_ThrowsException() {
        // ACT & ASSERT (Pattern de ton cours avec l'expression lambda)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderManager.calculateTableAddition(0); // Le numéro 0 est interdit
        });

        // Vérification du message d'erreur exact écrit dans ton code
        assertEquals("The table number must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testCalculateTableAddition_NegativeTableNumber_ThrowsException() {
        // ACT & ASSERT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderManager.calculateTableAddition(-5); // Un numéro négatif est interdit
        });

        assertEquals("The table number must be greater than 0.", exception.getMessage());
    }
}