package ca.sfu.pluto;

// import main.java.Functions.LocalDateFunctions;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


import java.time.LocalDate;

/**
 * This class has functions for interacting with the MongoDB.
 * The code in each function will search the DB (or modify it).
 * If we're dealing with dates (such as a range of dates for #commits),
 * the LocalDate class could be used.
 */

public class DatabaseFunctions {

    private static String mongoDBConnectionAddress = "mongodb+srv://Kae:mongopass@plutocluster.nop8i.mongodb.net/gitlab?retryWrites=true&w=majority";

    public static boolean isUserAuthenticated(/* args? */) {
        /* This function gets the user to enter some kind of password, and checks
           somewhere in the DB that this password is correct. Then, they get access
           to their token (which is also stored in the DB). */

        return false; // Placeholder
    }

    /**
     * Searches the database for the token associated with the given username
     *
     * @param username username to identify whose token is being retrieved
     * @return The associated username's token as a String. Returns empty string if no token is found.
     */
    public static String retrieveUserToken(String username) {
        try (MongoClient mongoClient = MongoClients.create(mongoDBConnectionAddress)) {

            MongoDatabase gitlabDB = mongoClient.getDatabase("gitlab");
            MongoCollection<Document> usersCollection = gitlabDB.getCollection("users");

            Document user = usersCollection.find(eq("username", username))
                    .projection(Projections.fields(Projections.include("token"), Projections.excludeId()))
                    .first();

            String token = user.getString("token");
            return token;
        }
    }

    /**
     * Adds a document to the collection store a token for the given username
     * If the user already exists, the token will be updated instead
     *
     * @param username username to identify whose token is being stored
     * @param token authentication token to be stored
     */
    public static void addUserToken(String username, String token) {
        try (MongoClient mongoClient = MongoClients.create(mongoDBConnectionAddress)) {
            MongoDatabase gitlabDB = mongoClient.getDatabase("gitlab");
            MongoCollection<Document> userCollection = gitlabDB.getCollection("users");

            // Setup filter and upsert options so that usernames remain unique.
            Bson filter = eq("username", username);
            Bson updateOperation = set("token", token);
            UpdateOptions options = new UpdateOptions().upsert(true);
            userCollection.updateOne(filter, updateOperation, options);
        }
    }

    public static int numCommits(String username, LocalDate startDate, LocalDate endDate) {
        /* Find the number of commits made by the user, from the start date to
           the end date. */

        int numTotalCommits = 0;

//        ArrayList<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates(startDate, endDate);

        /* Run through all dates in this list. For each of them, search the DB for that
           user and that date, adding the # commits to the sum? */

        return numTotalCommits;
    }

    public static void setNumCommits(String username, LocalDate date, int numCommits) {
        /* In the DB, for the specified user on the given date, set the number of commits
           to numCommits.

           Check whether there is already a value for this user at this date? If so,
           decide what to do. */
    }

    public static int numMergeRequests(String username, LocalDate startDate, LocalDate endDate) {
        // Same idea as the numCommits() function, just for MRs.

        int numTotalMergeRequests = 0;

//        ArrayList<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates
//                                              (startDate, endDate);

        return numTotalMergeRequests;
    }

    public static void setNumMergeRequests(String username, LocalDate date, int numMRs) {
        // Similar idea to the setNumCommits() function.
    }
}
