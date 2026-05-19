package dataAccess;

import exception.ReadException;
import model.Employee;
import java.util.ArrayList;

public interface EmployeeDataAccess {
    ArrayList<Employee> readAll() throws ReadException;
    Employee readById(int id) throws ReadException;
}