package test.java.DatabaseClasses;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import main.java.Collections.User;
import main.java.DatabaseClasses.DatabaseFunctions;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

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
        assertEquals("gklP3oD95mxDs2lFk6Hy4", DatabaseFunctions.retrieveUserToken("test"));
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
        User user = new User();
        user.setName("Farragut");
        user.setPassword("uss");
        user.setToken("DDG-37");
        DatabaseFunctions.createUserAccount(user);
        User c_user = DatabaseFunctions.retrieveUserInfo("Farragut");
        String expectedAnswer=c_user.getUsername()+c_user.getPassword()+c_user.getToken();
        String correctAnswer= "FarragutVFUUwPHbmuEztQ1FQ6IzJfxyV+OT9vvZatMKLUrOpRtndZfb1k7CI1b1i40NMcs6s9KNrmHNE3MgrFcEVq3S1A==DDG-37";
        assertEquals(correctAnswer, expectedAnswer);
    }

    /**
     * test for setting the number of commits on certain dates, and getting
     * the number of commits over a given period.
     */
    @Test
    public void testNumCommits() {
        DatabaseFunctions.setNumCommits("Bob", LocalDateTime.of(2020, 10, 15,0,0), 6);
        DatabaseFunctions.setNumCommits("Bob", LocalDateTime.of(2020, 2, 3,0,0), 150);
        assertEquals(156, DatabaseFunctions.numCommits("Bob", LocalDateTime.of(2020, 1, 1,0,0),
                LocalDateTime.of(2021, 1, 2,0,0)));
    }

    /**
     * test for setting the number of merge requests on certain dates, and getting
     * the number of merge requests over a given period.
     */
    @Test
    public void testNumMergeRequests() {
        DatabaseFunctions.setNumMergeRequests("Bob", LocalDateTime.of(2021, 5, 17,0,0), 2);
        DatabaseFunctions.setNumMergeRequests("Bob", LocalDateTime.of(2021, 5, 17,0,0), 3);
        // should overwrite.
        DatabaseFunctions.setNumMergeRequests("Bob", LocalDateTime.of(2021, 6, 20,0,0), 1);
        assertEquals(4, DatabaseFunctions.numMergeRequests("Bob", LocalDateTime.of(2021, 1, 1,0,0),
                LocalDateTime.of(2021, 12, 31,0,0)));
    }
}

