package main.java.Functions;

import java.time.LocalDate;
import java.util.ArrayList;

public class LocalDateFunctions {

    public static ArrayList<LocalDate> generateRangeOfDates(LocalDate startDate,
                                                     LocalDate endDate) {
        ArrayList<LocalDate> rangeOfDates = new ArrayList<LocalDate>();

        rangeOfDates.add(startDate);

        while (!startDate.equals(endDate)) {
            startDate = startDate.plusDays(1);

            rangeOfDates.add(startDate);
        }

        return rangeOfDates;
    }
}