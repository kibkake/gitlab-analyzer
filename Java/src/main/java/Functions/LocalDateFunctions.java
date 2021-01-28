package main.java.Functions;

import java.time.LocalDate;
import java.util.ArrayList;

public class LocalDateFunctions {

    // Continue here - JUnit test this function.

    // Will return an ArrayList containing dates from startDate to endDate, inclusive.
    // If startDate is greater than endDate, an empty ArrayList is returned.
    public static ArrayList<LocalDate> generateRangeOfDates(LocalDate startDate,
                                                            LocalDate endDate) {
        ArrayList<LocalDate> rangeOfDates = new ArrayList<>();

        if (startDate.compareTo(endDate) > 0) {
            return rangeOfDates;
        }

        rangeOfDates.add(startDate);

        while (!startDate.equals(endDate)) {
            startDate = startDate.plusDays(1);

            rangeOfDates.add(startDate);
        }

        return rangeOfDates;
    }
}