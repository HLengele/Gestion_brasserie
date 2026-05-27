package model;

import exception.NullValueException;
import java.time.LocalDate;

public class Employee {

    private Integer employeeId;
    private String lastName;
    private String firstName;
    private LocalDate hiringDate;

    private City city;

    public Employee(Integer employeeId, String firstName, String lastName, LocalDate hiringDate, City city)
            throws NullValueException {
        setEmployeeId(employeeId);
        setFirstName(firstName);
        setLastName(lastName);
        setHiringDate(hiringDate);
        setCity(city);
    }


    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws NullValueException {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        } else {
            throw new NullValueException("The employee's first name cannot be empty");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws NullValueException {
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        } else {
            throw new NullValueException("The employee's last name cannot be empty");
        }
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Order takeOrder(Table table) throws NullValueException {
        if (table == null) {
            throw new NullValueException("Cannot take an order for a null table");
        }

        return new Order(
                0,
                java.time.LocalTime.now(),
                table.getTableNumber()
        );
    }

    public void collect(Order order) throws NullValueException {
        if (order != null) {
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}