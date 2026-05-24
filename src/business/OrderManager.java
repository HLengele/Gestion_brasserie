package business;

import dataAccess.OrderDataAccess;
import dataAccess.OrderDBAccess;
import exception.ReadException;

public class OrderManager {

    // On passe par l'interface (Design Pattern DAO)
    private OrderDataAccess orderDao;

    public OrderManager() {
        // Instanciation de l'implémentation concrète
        this.orderDao = new OrderDBAccess();
    }

    // Ta méthode métier devient extrêmement propre et découplée de la BD
    public double calculateTableAddition(int tableNumber) throws ReadException {
        if (tableNumber <= 0) {
            throw new IllegalArgumentException("Le numéro de table doit être supérieur à 0.");
        }

        // Appel transparent à la couche d'accès aux données
        return orderDao.getTotalPriceByTable(tableNumber);
    }
}