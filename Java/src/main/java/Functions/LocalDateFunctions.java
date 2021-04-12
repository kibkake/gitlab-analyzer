package main.java.Functions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/*
References:

- https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
  Used the first answer to figure out how to convert a Date to LocalDate.
 */

/**
 * This class contains helper functions which work with the LocalDate class.
 * Any class in the application can call it if it's useful.
 */

public class LocalDateFunctions {

    /* Continue here - JUnit test this function.

       Will return an ArrayList containing dates from startDate to endDate, inclusive.
       If startDate is greater than endDate, an empty ArrayList is returned. */
    public static ArrayList<LocalDateTime> generateRangeOfDates(LocalDateTime startDate,
                                                            LocalDateTime endDate) {
        ArrayList<LocalDateTime> rangeOfDates = new ArrayList<>();

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

    public static LocalDateTime convertDateToLocalDate(Date date) {
        LocalDateTime LocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return LocalDateTime;
    }
}