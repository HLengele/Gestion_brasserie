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
        else throw new NullValueException("The hour cannot be null");
    }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) throws NullValueException {
        if (tableNumber > 0) this.tableNumber = tableNumber;
        else throw new NullValueException("The table number must be greater than 0");
    }

    @Override
    public String toString() {
        return "Order n°" + orderId + " - Table " + tableNumber + " at " + hour;
    }
}