    package business;

    import dataAccess.*;
    import exception.*;
    import model.*;

    import java.util.ArrayList;

    public class OrderManager {

        private OrderDataAccess orderDao;
        private CityDataAccess cityDao;
        private EmployeeDataAccess employeeDao;
        private TableDataAccess tableDao;

        public OrderManager() {
            setOrderDao(new OrderDBAccess());
            setCityDao(new CityDBAccess());
            setEmployeeDao(new EmployeeDBAccess());
            setTableDao(new TableDBAccess());
        }

        public void setOrderDao(OrderDataAccess orderDao) { this.orderDao = orderDao; }
        public void setCityDao(CityDataAccess cityDao) { this.cityDao = cityDao; }
        public void setEmployeeDao(EmployeeDataAccess employeeDao) { this.employeeDao = employeeDao; }
        public void setTableDao(TableDataAccess tableDao) { this.tableDao = tableDao; }


        public int addOrder(Order newOrder) throws AddOrderException {
            return orderDao.insertOrder(newOrder);
        }

        public void addLineOrder(int orderId, int beerId, int quantity, double realPrice) throws Exception {
            orderDao.insertLineOrder(orderId, beerId, quantity, realPrice);
        }

        public ArrayList<Table> getAllTables() throws ReadException {
            return tableDao.readAll();
        }

        public double calculateTableAddition(int tableNumber) throws ReadException {
            if (tableNumber <= 0) {
                throw new IllegalArgumentException("The table number must be greater than 0.");
            }
            return orderDao.getTotalPriceByTable(tableNumber);
        }
    }