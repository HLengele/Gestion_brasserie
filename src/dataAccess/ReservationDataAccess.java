package dataAccess;

import exception.ReadException;
import model.ReservationSearchResult;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReservationDataAccess {
    ArrayList<ReservationSearchResult> getReservationsBetweenDates(LocalDate startDate, LocalDate endDate) throws ReadException;
}