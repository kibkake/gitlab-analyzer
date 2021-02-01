package ca.sfu.pluto;

import main.java.Functions.LocalDateFunctions;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class has functions for interacting with the MongoDB.
 * The code in each function will search the DB (or modify it).
 * If we're dealing with dates (such as a range of dates for #commits),
 * the LocalDate class could be used.
 */

public class DatabaseFunctions {

    public static boolean isUserAuthenticated(/* args? */) {
        /* This function gets the user to enter some kind of password, and checks
           somewhere in the DB that this password is correct. Then, they get access
           to their token (which is also stored in the DB). */

        return false; // Placeholder
    }

    public static String retrieveUserToken(String userName) {
        // Search the database for the token (a String?) matching userName.

        return ""; // Placeholder
    }

    public static void addUserToken(String userName, String token) {
        // Make a new row in some table of the DB, to store userName and token together.
    }

    public static int numCommits(String userName, LocalDate startDate, LocalDate endDate) {
        /* Find the number of commits made by the user, from the start date to
           the end date. */

        int numTotalCommits = 0;

        ArrayList<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates(startDate, endDate);

        /* Run through all dates in this list. For each of them, search the DB for that
           user and that date, adding the # commits to the sum? */

        return numTotalCommits;
    }

    public static void setNumCommits(String userName, LocalDate date, int numCommits) {
        /* In the DB, for the specified user on the given date, set the number of commits
           to numCommits.

           Check whether there is already a value for this user at this date? If so,
           decide what to do. */
    }

    public static int numMergeRequests(String userName, LocalDate startDate, LocalDate endDate) {
        // Same idea as the numCommits() function, just for MRs.

        int numTotalMergeRequests = 0;

        ArrayList<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates
                                              (startDate, endDate);

        return numTotalMergeRequests;
    }

    public static void setNumMergeRequests(String userName, LocalDate date, int numMRs) {
        // Similar idea to the setNumCommits() function.
    }
}
