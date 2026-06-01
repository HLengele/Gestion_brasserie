package business;

import dataAccess.ReservationDataAccess;
import exception.ReadException;
import model.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationManager implements IReservationManager {

    private ReservationDataAccess reservationDao;

    public ReservationManager(ReservationDataAccess reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ArrayList<Reservation> searchReservationsBetweenDates(LocalDate start, LocalDate end) throws ReadException {
        if (start.isAfter(end)) {
            throw new ReadException("The start date must be before or equal to the end date.");
        }
        return reservationDao.getReservationsBetweenDates(start, end);
    }
}