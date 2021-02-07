package test.java.DatabaseClasses;

import org.junit.Test;
import static org.junit.Assert.*;
import main.java.DatabaseClasses.DatabaseFunctions;

/**
 * JUnit test class to test DatabaseFunctions class
 */
public class DatabaseFunctionTest{

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
}

