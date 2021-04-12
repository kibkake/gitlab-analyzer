package test.java.Functions;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static main.java.Functions.LocalDateFunctions.generateRangeOfDates;
import static org.junit.Assert.assertTrue;

/**
 * This class does JUnit tests for the functions in the LocalDateFunctions class.
 */

public class LocalDateFunctionsTest {

    @Test
    public void testRangeOfDatesBiggerStartDate() {
        LocalDateTime start = LocalDateTime.of(2021, 1, 1,0,0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31,0,0);

        ArrayList<LocalDateTime> emptyList = new ArrayList<>();

        assertTrue(emptyList.equals(generateRangeOfDates(start, end)));
    }

    @Test
    public void testSameDate() {
        LocalDateTime start = LocalDateTime.of(1999, 5, 5,0,0);
        LocalDateTime end = LocalDateTime.of(1999, 5, 5,0,0);

        ArrayList<LocalDateTime> singleton = new ArrayList<>();
        singleton.add(start);

        assertTrue(singleton.equals(generateRangeOfDates(start, end)));
    }

    @Test
    public void testWholeYear() {
        /* For this test function, look into how to run it multiple times with different
           LocalDate objects. */

        // Non-leap year:
        LocalDateTime start2010 = LocalDateTime.of(2010, 1, 1,0,0);
        LocalDateTime end2010 = LocalDateTime.of(2010, 12, 31,0,0);

        ArrayList<LocalDateTime> list2010 = generateRangeOfDates(start2010, end2010);

        assertTrue((list2010.size() == 365 && !start2010.toLocalDate().isLeapYear()) ||
                   (list2010.size() == 366 && start2010.toLocalDate().isLeapYear()));
        assertTrue(list2010.get(0).getDayOfYear() == 1);
        assertTrue(list2010.get(list2010.size()-1).getDayOfYear() == list2010.size());

        assertTrue(allDaysInOrder(list2010, start2010.toLocalDate().isLeapYear()));

        // Leap year:

        LocalDateTime start2012 = LocalDateTime.of(2012, 1, 1,0,0);
        LocalDateTime end2012 = LocalDateTime.of(2012, 12, 31,0,0);

        ArrayList<LocalDateTime> list2012 = generateRangeOfDates(start2012, end2012);

        assertTrue((list2012.size() == 365 && !start2012.toLocalDate().isLeapYear()) ||
                   (list2012.size() == 366 && start2012.toLocalDate().isLeapYear()));
        assertTrue(list2012.get(0).getDayOfYear() == 1);
        assertTrue(list2012.get(list2012.size()-1).getDayOfYear() == list2012.size());

        assertTrue(allDaysInOrder(list2012, start2012.toLocalDate().isLeapYear()));
    }

    // Helper functions for testing:

    public boolean isFirstDayOfMonth(int dayNumber, boolean isLeapYear) {
        if (dayNumber == 1 || dayNumber == 32) {
            return true;
        }

        if (isLeapYear) {
            dayNumber--;
        }

        return dayNumber == 60 || dayNumber == 91 || dayNumber == 121 ||
                dayNumber == 152 || dayNumber == 182 || dayNumber == 213 ||
                dayNumber == 244 || dayNumber == 274 || dayNumber == 305 ||
                dayNumber == 335;
    }

    public boolean allDaysInOrder(ArrayList<LocalDateTime> list, boolean isLeapYear) {
        int dayCounter = 0;
        int monthCounter = 0;

        for (int i = 0; i < list.size(); i++)
        {
            dayCounter++;

            if (isFirstDayOfMonth(dayCounter, isLeapYear)) {
                monthCounter++;
            }

            if (list.get(i).getDayOfYear() != dayCounter ||
                list.get(i).getMonthValue() != monthCounter) {
                return false;
            }
        }

        return true;
    }
}