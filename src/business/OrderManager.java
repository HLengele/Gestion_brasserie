package business;

import exception.ReadException;
import model.Order;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class OrderManager {
    private OrderDataAccess orderDBAccess;
    private UserDataAccess userDBAccess;
    private StatusDataAccess statusDBAccess;
    private AddressDBAccess addressDBAccess;
    private SearchDatesDBAccess searchDatesDBAccess;

    public OrderManager() {
        setOrderDBAccess(new OrderDBAccess());
        setUserDBAccess(new UserDBAccess());
        setStatusDBAccess(new StatusDBAccess());
        setAddressDBAccess(new AddressDBAccess());
        setSearchDatesDBAccess(new SearchDatesDBAccess());
    }

    public void setOrderDBAccess(OrderDataAccess orderDBAccess) {
        this.orderDBAccess = orderDBAccess;
    }

    public void setUserDBAccess(UserDataAccess userDBAccess) {
        this.userDBAccess = userDBAccess;
    }

    public void setStatusDBAccess(StatusDataAccess statusDBAccess) {
        this.statusDBAccess = statusDBAccess;
    }

    public void setAddressDBAccess(AddressDBAccess addressDBAccess) {
        this.addressDBAccess = addressDBAccess;
    }

    public void setSearchDatesDBAccess(SearchDatesDBAccess searchDatesDBAccess) {
        this.searchDatesDBAccess = searchDatesDBAccess;
    }

    public ArrayList<Order> getAllOrders() throws ReadException, NoteLengthException, NullValueException, InvalidDatesException {
        return orderDBAccess.readAll();
    }

    public User getUserById(int id) throws ReadException {
        return userDBAccess.readById(id);
    }

    public Status getStatusById(int id) throws ReadException {
        return statusDBAccess.readById(id);
    }

    public Address getAddressById(int id) throws ReadException {
        return addressDBAccess.readById(id);
    }

    public ArrayList<SearchModel> searchByDates(GregorianCalendar startDate, GregorianCalendar endDate) throws SearchException, InvalidDatesException {
        if (startDate.compareTo(endDate) > 0) {
            throw new InvalidDatesException("startDate must be before endDate");
        }
        return searchDatesDBAccess.searchByDates(startDate, endDate);
    }

    public ArrayList<Status> getAllStatus() throws ReadException {
        return statusDBAccess.readAll();
    }

    public ArrayList<Address> getAllAddresses() throws ReadException {
        return addressDBAccess.readAll();
    }

    public ArrayList<User> getAllUsers() throws ReadException {
        return userDBAccess.readAll();
    }

    public void addOrder(Order newOrder) throws AddOrderException {
        orderDBAccess.insertOrder(newOrder);
    }

    public void deleteOrder(Integer id) throws DeleteOrderException {
        orderDBAccess.deleteOrder(id);
    }

    public Order getOrderById(int id) throws ReadException, NoteLengthException, NullValueException, InvalidDatesException {
        return orderDBAccess.readById(id);
    }

    public void updateOrder(Order orderToUpdate) throws UpdateOrderException {
        orderDBAccess.updateOrder(orderToUpdate);
    }
}
