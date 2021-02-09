package test.java.DatabaseClasses;

import org.junit.Test;
import static org.junit.Assert.*;
import main.java.DatabaseClasses.DatabaseFunctions;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.time.Duration;
import java.time.LocalDate;
/**
 * JUnit test class to test DatabaseFunctions class
 */
public class DatabaseFunctionsTest{

    /*
        inserted static code here to disable MongoDB driver console logging for normal events.
     */
    static{
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.WARN);
    }

    /**
     * Test for adding and retrieving tokens
     */
    @Test
    public void testTokens(){
        // test add and read tokens
        DatabaseFunctions.addUserToken("test","gklP3oD95mxDs2lFk6Hy4");
        assertEquals("gklP3oD95mxDs2lFk6Hy4",DatabaseFunctions.retrieveUserToken("test"));
    }

    /**
     * Test for checking authentication
     */
    @Test
    public void testIsAuthenticated(){
        /* tests user password with database stored password
                user: Enterprise
                pass: thegreyghost
         */

        // Testing wrong password = thegrayghost
        assertFalse(DatabaseFunctions.isUserAuthenticated("Enterprise","thegrayghost"));

        // Testing correct password = thegreyghost
        assertTrue(DatabaseFunctions.isUserAuthenticated("Enterprise","thegreyghost"));
    }

    /**
     * test for account creation
     */
    @Test
    public void testAccountCreation(){
        /* tests user account creation
                user: Farragut
                pass: uss
                token: DDG-37
         */

        DatabaseFunctions.createUserAccount("Farragut","uss","DDG-37");
        String correctAnswer= "Farragut\nVFUUwPHbmuEztQ1FQ6IzJfxyV+OT9vvZatMKLUrOpRtndZfb1k7CI1b1i40NMcs6s9KNrmHNE3MgrFcEVq3S1A==\nDDG-37";
        assertEquals(correctAnswer,DatabaseFunctions.retrieveUserInfo("Farragut"));
    }

    /**
     * test for setting the number of commits on certain dates, and getting
     * the number of commits over a given period.
     */
    @Test
    public void testNumCommits() {
        DatabaseFunctions.setNumCommits("Bob", LocalDate.of(2020, 10, 15), 6);
        DatabaseFunctions.setNumCommits("Bob", LocalDate.of(2020, 2, 3), 150);
        assertEquals(156, DatabaseFunctions.numCommits("Bob", LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 2)));
    }

    /**
     * test for setting the number of merge requests on certain dates, and getting
     * the number of merge requests over a given period.
     */
    @Test
    public void testNumMergeRequests() {
        DatabaseFunctions.setNumMergeRequests("Bob", LocalDate.of(2021, 5, 17), 2);
        DatabaseFunctions.setNumMergeRequests("Bob", LocalDate.of(2021, 5, 17), 3);
        // should overwrite.
        DatabaseFunctions.setNumMergeRequests("Bob", LocalDate.of(2021, 6, 20), 1);
        assertEquals(4, DatabaseFunctions.numMergeRequests("Bob", LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 12, 31)));
    }
}

