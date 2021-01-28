package Functions;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static main.java.Functions.LocalDateFunctions.generateRangeOfDates;
import static org.junit.Assert.*;

public class LocalDateFunctionsTest {

    @Test
    public void testRangeOfDatesBiggerStartDate() {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate end = LocalDate.of(2020, 12, 31);

        ArrayList<LocalDate> emptyList = new ArrayList<>();

        assertTrue(emptyList.equals(generateRangeOfDates(start, end)));
    }

    @Test
    public void testSameDate() {
        LocalDate start = LocalDate.of(1999, 5, 5);
        LocalDate end = LocalDate.of(1999, 5, 5);

        ArrayList<LocalDate> singleton = new ArrayList<>();
        singleton.add(start);

        assertTrue(singleton.equals(generateRangeOfDates(start, end)));
    }

    @Test
    public void testWholeYear() {
        /* For this test function, look into how to run it multiple times with different
           LocalDate objects. */

        // Non-leap year:
        LocalDate start2010 = LocalDate.of(2010, 1, 1);
        LocalDate end2010 = LocalDate.of(2010, 12, 31);

        ArrayList<LocalDate> list2010 = generateRangeOfDates(start2010, end2010);

        assertTrue((list2010.size() == 365 && !start2010.isLeapYear()) ||
                   (list2010.size() == 366 && start2010.isLeapYear()));
        assertTrue(list2010.get(0).getDayOfYear() == 1);
        assertTrue(list2010.get(list2010.size()-1).getDayOfYear() == list2010.size());

        assertTrue(allDaysInOrder(list2010, start2010.isLeapYear()));

        // Leap year:

        LocalDate start2012 = LocalDate.of(2012, 1, 1);
        LocalDate end2012 = LocalDate.of(2012, 12, 31);

        ArrayList<LocalDate> list2012 = generateRangeOfDates(start2012, end2012);

        assertTrue((list2012.size() == 365 && !start2012.isLeapYear()) ||
                   (list2012.size() == 366 && start2012.isLeapYear()));
        assertTrue(list2012.get(0).getDayOfYear() == 1);
        assertTrue(list2012.get(list2012.size()-1).getDayOfYear() == list2012.size());

        assertTrue(allDaysInOrder(list2012, start2012.isLeapYear()));
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

    public boolean allDaysInOrder(ArrayList<LocalDate> list, boolean isLeapYear) {
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