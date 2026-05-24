package business;

import dataAccess.ReservationDBAccess;
import dataAccess.ReservationDataAccess;
import exception.ReadException;
import model.ReservationSearchResult;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationManager {
    private ReservationDataAccess reservationDao;

    public ReservationManager() {
        setReservationDao(new ReservationDBAccess());
    }

    public void setReservationDao(ReservationDataAccess reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ArrayList<ReservationSearchResult> searchReservationsBetweenDates(LocalDate start, LocalDate end) throws ReadException {
        if (start.isAfter(end)) {
            throw new ReadException("La date de début doit être antérieure ou égale à la date de fin.");
        }
        return reservationDao.getReservationsBetweenDates(start, end);
    }
}