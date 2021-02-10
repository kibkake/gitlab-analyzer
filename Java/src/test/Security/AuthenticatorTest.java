package test.Security;

import org.junit.Test;
import static org.junit.Assert.*;
import main.java.Security.Authenticator;

/**
 * JUnit Test class for Authenticator class
 */
public class AuthenticatorTest{

    /**
     * tests the password encrypted "Montana" to various similar Strings
     */
    @Test
    public void testPasswordEncrypt(){

        String pKey = Authenticator.encrypt("Montana");
        /* Testing of Password encryption:
                password = Montana
                key = T5ENUbT86+oJAsaIoKzofNYB4p0Kr4EpXrb5psYLdIyGhKxP44+KH5Ypj16cp0JSV/mQetMYTlLBVYzsFVSQbg==
         */

        // try password = "montana"
        assertFalse(pKey.equals(Authenticator.encrypt("montana")));

        // try password = "_montana"
        assertFalse(pKey.equals(Authenticator.encrypt("_montana")));

        // try password = " montana"
        assertFalse(pKey.equals(Authenticator.encrypt(" montana")));

        // try password = "Idaho"
        assertFalse(pKey.equals(Authenticator.encrypt("Idaho")));

        // try correct password = "Montana"
        assertTrue(pKey.equals(Authenticator.encrypt("Montana")));
        assertEquals(pKey,Authenticator.encrypt("Montana"));
    }
}