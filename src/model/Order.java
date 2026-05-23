package model;

import exception.NullValueException;
import java.time.LocalTime;

public class Order {
    private Integer orderId;
    private LocalTime hour;
    private int tableNumber;

    public Order(Integer orderId, LocalTime hour, int tableNumber) throws NullValueException {
        setOrderId(orderId);
        setHour(hour);
        setTableNumber(tableNumber);
    }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public LocalTime getHour() { return hour; }
    public void setHour(LocalTime hour) throws NullValueException {
        if (hour != null) this.hour = hour;
        else throw new NullValueException("L'heure ne peut pas être nulle");
    }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) throws NullValueException {
        if (tableNumber > 0) this.tableNumber = tableNumber;
        else throw new NullValueException("Le numéro de table doit être supérieur à 0");
    }

    @Override
    public String toString() {
        return "Commande n°" + orderId + " - Table " + tableNumber + " à " + hour;
    }
}