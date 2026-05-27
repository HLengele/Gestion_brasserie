package dataAccess;

import exception.ReadException;
import model.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReservationDataAccess {
    ArrayList<Reservation> getReservationsBetweenDates(LocalDate startDate, LocalDate endDate) throws ReadException;
}