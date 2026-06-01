import dataAccess.*;
import business.*;
import controller.ApplicationController;
import controller.IApplicationController;
import userInterface.MainWindow;

public class Main {
    public static void main(String[] args) {

        BeerDataAccess beerDao = new BeerDBAccess();
        CategoryDataAccess categoryDao = new CategoryDBAccess();
        OrderDataAccess orderDao = new OrderDBAccess();
        CityDataAccess cityDao = new CityDBAccess();
        EmployeeDataAccess employeeDao = new EmployeeDBAccess();
        TableDataAccess tableDao = new TableDBAccess();
        ReservationDataAccess reservationDao = new ReservationDBAccess();

        IBeerManager beerManager = new BeerManager(beerDao);
        ICategoryManager categoryManager = new CategoryManager(categoryDao);
        IReservationManager reservationManager = new ReservationManager(reservationDao);
        IOrderManager orderManager = new OrderManager(orderDao, cityDao, employeeDao, tableDao);

        IApplicationController controller = new ApplicationController(
                orderManager,
                categoryManager,
                reservationManager,
                beerManager
        );

        MainWindow mainWindow = new MainWindow(controller);

    }
}