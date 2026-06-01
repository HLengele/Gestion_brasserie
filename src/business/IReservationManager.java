package business;

import exception.ReadException;
import model.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IReservationManager {
    ArrayList<Reservation> searchReservationsBetweenDates(LocalDate start, LocalDate end) throws ReadException;
}