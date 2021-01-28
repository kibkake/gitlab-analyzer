package Functions;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void testLargeRangeOfDates() {
        LocalDate start = LocalDate.of(2010, 1, 1);
        LocalDate end = LocalDate.of(2010, 12, 31);

        ArrayList<LocalDate> list = generateRangeOfDates(start, end);

        assertTrue((list.size() == 365 && !start.isLeapYear()) ||
                   (list.size() == 366 && start.isLeapYear()));
        assertTrue(list.get(0).getDayOfYear() == 1);
        assertTrue(list.get(list.size()-1).getDayOfYear() == list.size());

        assertTrue(allDaysInOrder(list, false));
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