package DatabaseClasses;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import main.java.Functions.LocalDateFunctions;
import main.java.Security.Authenticator;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/*
References:

- https://www.tutorialspoint.com/how-to-delete-a-mongodb-document-using-java
  This page and the other tabs under "Related Questions and Answers" provided a useful reference
  for using MongoDB functions in Java.

- https://mongodb.github.io/mongo-java-driver/3.11/javadoc/org/bson/Document.html
  Provided documentation for using org.bson.Document.

- https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
  Documentation on the LocalDate class and its functions.
*/

/**
 * This class has functions for interacting with the MongoDB.
 * The code in each function will search the DB (or modify it).
 * If we're dealing with dates (such as a range of dates for #commits),
 * the LocalDate class could be used.
 */

public class DatabaseFunctions {

    private final static String mongoDBConnectionAddress = "mongodb+srv://Kae:mongopass@plutocluster.nop8i.mongodb.net/gitlab?retryWrites=true&w=majority";
    private static MongoClient mongoClient;
    private static MongoDatabase gitlabDB = connectToDatabase();

    /**
     * Initializes the gitlabDB and mongoClient static objects.
     * @return A MongoDatabase object, that gitlabDB is set equal to.
     */
    private static MongoDatabase connectToDatabase() {
        mongoClient = MongoClients.create(mongoDBConnectionAddress);
        return mongoClient.getDatabase("gitlab");
    }

    /**
     * Creates a user account with an optional token and stores it securely on the database.
     * @param username the unique username to be created
     * @param password the password for the account
     * @param token (optional) the token used to access gitlab api.
     */
    public static void createUserAccount(String username, String password, String token){
        MongoCollection<Document> userCollection = gitlabDB.getCollection("users");

        // Setup filter and upsert options so that usernames remain unique.
        Bson filter = eq("username", username);
        // sets the password
        Bson updateOperation = set("password", Authenticator.encrypt(password));
        UpdateOptions options = new UpdateOptions().upsert(true);
        userCollection.updateOne(filter, updateOperation, options);
        // update or set token
        if (token!=null){
            updateOperation = set("token", token);
        }
        userCollection.updateOne(filter, updateOperation, options);
    }

    /**
     * retrieves information regarding the users from the database. Usually, only used for testing purposes.
     * @param username the unique username of the user
     * @return a line by line string of the entries of the username, encrypted password, and token from the database.
     */
    public static String retrieveUserInfo(String username){
        MongoCollection<Document> userCollection = gitlabDB.getCollection("users");
        Document user = userCollection.find(eq("username", username)).first();
        return user.getString("username")+"\n"+user.getString("password")+"\n"+user.getString("token");
    }

    /**
     * checks to see if the user is authenticated by comparing encrypted password strings between the raw and the database one.
     * @param username the uninque username of the user
     * @param password the raw password
     * @return true if encrypted password matches, false otherwise.
     */
    public static boolean isUserAuthenticated(String username, String password) {
        MongoCollection<Document> usersCollection = gitlabDB.getCollection("users");
        Document user = usersCollection.find(eq("username", username))
                .projection(Projections.fields(Projections.include("password"), Projections.excludeId()))
                .first();
        String pass = user.getString("password");
        // matches encrypted password for security reasons
        return Authenticator.encrypt(password).equals(pass);
    }

    /**
     * Searches the database for the token associated with the given username
     *
     * @param username username to identify whose token is being retrieved
     * @return The associated username's token as a String. Returns empty string if no token is found.
     */
    public static String retrieveUserToken(String username) {
        MongoCollection<Document> usersCollection = gitlabDB.getCollection("users");

        Document user = usersCollection.find(eq("username", username))
                .projection(Projections.fields(Projections.include("token"), Projections.excludeId()))
                .first();

        String token = user.getString("token");
        return token;
    }

    /**
     * Adds a document to the collection store a token for the given username
     * If the user already exists, the token will be updated instead
     *
     * @param username username to identify whose token is being stored
     * @param token authentication token to be stored
     */
    public static void addUserToken(String username, String token) {
        MongoCollection<Document> userCollection = gitlabDB.getCollection("users");

        // Setup filter and upsert options so that usernames remain unique.
        Bson filter = eq("username", username);
        Bson updateOperation = set("token", token);
        UpdateOptions options = new UpdateOptions().upsert(true);
        userCollection.updateOne(filter, updateOperation, options);
    }

    /**
     * Searches for a document storing the token for the user, and if it exists,
     * it is removed from the "users" collection.
     * @param username The user we're looking for.
     * @param token Their current token.
     */
    public static void removeUserToken(String username, String token) {
        MongoCollection<Document> usersCollection = gitlabDB.getCollection("users");

        Bson deleteCondition = and(eq("username", username), eq("token", token));

        usersCollection.deleteOne(deleteCondition);
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
        MongoCollection<Document> usersCollection = gitlabDB.getCollection("commits");

        int numTotalCommits = 0;

        List<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates(startDate, endDate);

        /* Run through all dates in this list. For each of them, search the DB for that
           user and that date, adding the # commits to the sum? */

        for (LocalDate currentDate : datesToExamine) {
            Document user = usersCollection.find(and(
                    eq("username", username),
                    eq("year", currentDate.getYear()),
                    eq("month", currentDate.getMonthValue()),
                    eq("day", currentDate.getDayOfMonth())))
                    .projection(Projections.fields(Projections.include("num_commits")))
                    .first();

            if (user != null) {
                int numCommits = user.getInteger("num_commits").intValue();
                numTotalCommits += numCommits;
            }
        }

        return numTotalCommits;
    }

    /**
     * Add a document to the commits Collection, storing the number of commits done
     * by the user on a specific date. If an entry already exists for this date,
     * it is overwritten.
     *
     * @param username Specifies the username attribute of the new/updated document.
     * @param date Specifies the date we're looking for. Is broken up into year, month, day.
     * @param numCommits Specifies the number of commits (an int value) to store. If it is
     *                   a negative value, the function throws an Exception.
     */
    public static void setNumCommits(String username, LocalDate date, int numCommits) throws IllegalArgumentException {
        if (numCommits < 0) {
            throw new IllegalArgumentException("numCommits param is negative in the setNumCommits() function");
        }

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

    /**
     * Return an int storing the number of merge requests the user has done, from
     * startDate to endDate.
     * @param username The user's username.
     * @param startDate The earliest date we're looking at.
     * @param endDate The latest date we're looking at.
     * @return The number of merge requests.
     */
    public static int numMergeRequests(String username, LocalDate startDate, LocalDate endDate) {
        MongoCollection<Document> usersCollection = gitlabDB.getCollection("mergeRequests");

        int numTotalMergeRequests = 0;

        List<LocalDate> datesToExamine = LocalDateFunctions.generateRangeOfDates(startDate, endDate);

        for (LocalDate currentDate : datesToExamine) {
            Document user = usersCollection.find(and(
                    eq("username", username),
                    eq("year", currentDate.getYear()),
                    eq("month", currentDate.getMonthValue()),
                    eq("day", currentDate.getDayOfMonth())))
                    .projection(Projections.fields(Projections.include("num_merge_requests")))
                    .first();

            if (user != null) {
                int numMergeRequests = user.getInteger("num_merge_requests").intValue();
                numTotalMergeRequests += numMergeRequests;
            }
        }

        return numTotalMergeRequests;
    }

    /**
     * Add a document to the mergeRequests collection, storing the user's username,
     * their number of merge requests, and the relevant date.
     * @param username User's username.
     * @param date The date they made the MRs in question.
     * @param numMergeRequests An int storing the number of MRs.
     * @throws IllegalArgumentException
     */
    public static void setNumMergeRequests(String username, LocalDate date,
                                           int numMergeRequests) throws IllegalArgumentException{
        if (numMergeRequests < 0) {
            throw new IllegalArgumentException("numMergeRequests param is negative in the setNumMergeRequests() function");
        }

        MongoCollection<Document> userCollection = gitlabDB.getCollection("mergeRequests");

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        Bson attributeValues = and(eq("username", username), eq("day", day),
                eq("month", month), eq("year", year));

        Bson updateOperation = set("num_merge_requests", numMergeRequests);

        UpdateOptions options = new UpdateOptions().upsert(true);
        userCollection.updateOne(attributeValues, updateOperation, options);
    }
}
