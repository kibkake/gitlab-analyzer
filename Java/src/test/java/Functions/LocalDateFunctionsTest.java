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

    public boolean allDaysInOrder(ArrayList<LocalDate> list, boolean isLeapYear) {
        int dayCounter = 1;

        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getDayOfYear() != dayCounter) {
                return false;
            }
            dayCounter++;
        }

        return true;
    }

    @Test
    public void testLargeRangeOfDates() {
        LocalDate start = LocalDate.of(2010, 1, 1);
        LocalDate end = LocalDate.of(2010, 12, 31);

        ArrayList<LocalDate> list = generateRangeOfDates(start, end);

        assertTrue(list.size() == 365);
        assertTrue(list.get(0).getDayOfYear() == 1);
        assertTrue(list.get(364).getDayOfYear() == 365);

        assertTrue(allDaysInOrder(list, false));
    }
}