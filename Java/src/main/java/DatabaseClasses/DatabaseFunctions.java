package main.java.DatabaseClasses;
import java.time.LocalDate;

public class DatabaseFunctions {

    private boolean isUserAuthenticated() {
        // This function gets the user to enter some kind of password, and checks
        // somewhere in the DB that this password is correct. Then, they get access
        // to their token (which is also stored in the DB).

        return false; // Placeholder
    }

    private String retrieveUserToken(String userName) {
        // Search the database for the token (a String?) matching userName.

        return ""; // Placeholder
    }

    private int numCommits(String userName, LocalDate startDate, LocalDate endDate) {
        // Find the number of commits made by the user, from the start date to
        // the end date.

        // To do this, run through all the dates in this period. For each of them,
        // search the DB for that user and that date, adding the # commits
        // to the sum?

        return 0;
    }

    private int numMergeRequests(String userName, LocalDate startDate, LocalDate endDate) {
        // Same idea as the numCommits() function, just for MRs.

        return 0;
    }
}
