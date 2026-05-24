package model;

import exception.NullValueException;
import java.time.LocalTime;

public class Customer {

    private int customerId;
    private String email;
    private String phone;
    private String name;

    public Customer(int customerId, String email, String phone, String name)
            throws NullValueException {
        setCustomerId(customerId);
        setEmail(email);
        setPhone(phone);
        setName(name);
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) throws NullValueException {
        if (customerId >= 0) {
            this.customerId = customerId;
        } else {
            throw new NullValueException("The customer ID cannot be negative");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws NullValueException {
        if (email != null && !email.isBlank()) {
            this.email = email;
        } else {
            throw new NullValueException("The customer's email cannot be empty");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NullValueException {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            throw new NullValueException("The customer's name cannot be empty");
        }
    }


    public Order reserveTable(LocalTime hour, Table table)
            throws NullValueException {
        if (table == null) {
            throw new NullValueException("Cannot reserve a null table");
        }

        return new Order(
                0,
                hour,
                table.getTableNumber()
        );
    }


    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}