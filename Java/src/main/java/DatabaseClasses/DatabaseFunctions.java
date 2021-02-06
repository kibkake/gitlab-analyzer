package main.java.DatabaseClasses;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.time.LocalDate;

import main.java.Functions.LocalDateFunctions;

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

    /**
     * Searches for a document storing the token for the user, and if it exists,
     * it is removed from the "users" collection.
     * @param username The user we're looking for.
     * @param token Their current token.
     */
    public static void removeUserToken(String username, String token) {
        try (MongoClient mongoClient = MongoClients.create(mongoDBConnectionAddress)) {
            MongoDatabase gitlabDB = mongoClient.getDatabase("gitlab");
            MongoCollection<Document> usersCollection = gitlabDB.getCollection("users");

            Bson deleteCondition = and(eq("username", username), eq("token", token));

            usersCollection.deleteOne(deleteCondition);
        }
    }

    /**
     * This function searches for all documents in the commits collection storing username,
     * and a date between startDate and endDate.
     * @param username is the username.
     * @param startDate The minimum date attribute for documents we're searching for.
     * @param endDate The maximum date attribute for documents.
     * @return The total number of commits between startDate and endDate.
     */
    public static int numCommits(String username, LocalDate startDate, LocalDate endDate) {
        try (MongoClient mongoClient = MongoClients.create(mongoDBConnectionAddress)) {
            MongoDatabase gitlabDB = mongoClient.getDatabase("gitlab");
            MongoCollection<Document> usersCollection = gitlabDB.getCollection("commits");

            int numTotalCommits = 0;

            List<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates(startDate, endDate);

            /* Run through all dates in this list. For each of them, search the DB for that
               user and that date, adding the # commits to the sum? */

            for (LocalDate currentDate : datesToExamine) {

            }

            return numTotalCommits;
        }
    }

    /**
     * Add a document to the commits Collection, storing the number of commits done
     * by the user on a specific date. If an entry already exists for this date,
     * it is overwritten.
     *
     * @param username Specifies the username attribute of the new/updated document.
     * @param date Specifies the date we're looking for. Is broken up into year, month, day.
     * @param numCommits Specifies the number of commits (an int value) to store.
     */
    public static void setNumCommits(String username, LocalDate date, int numCommits) {
        try (MongoClient mongoClient = MongoClients.create(mongoDBConnectionAddress)) {
            MongoDatabase gitlabDB = mongoClient.getDatabase("gitlab");
            MongoCollection<Document> userCollection = gitlabDB.getCollection("commits");

            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();

            Bson attributeValues = and(eq("username", username), eq("day", day),
                    eq("month", month), eq("year", year));

            Bson updateOperation = set("num_commits", numCommits);

            UpdateOptions options = new UpdateOptions().upsert(true);
            userCollection.updateOne(attributeValues, updateOperation, options);
        }
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
