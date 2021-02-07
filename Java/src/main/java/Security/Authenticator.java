package main.java.Security;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * This class allows for static calling of encrypt to generate a key from a given password.
 * The generated key will be stored in the database securely.
 * When users need to authenticate, the password that they enter will be encrypted first, then checked with the database.
 *
 * This class follows a similar flow from the following resource: <a href="https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh">DEV</a>
 *
 * @see  This class makes use of the SHA-2 Encryption: <a href="https://en.wikipedia.org/wiki/SHA-2">SHA-2/a>
 */
public final class Authenticator{
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final byte[] CRYPTOSALT = ("gMsLe2L49faE23lS").getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final int KEY_SIZE = 512;
    private static final int ITERATIONS = 100000;

    /**
     * Encrypts the given password and returns an encrypted key (safe for storage)
     * @param password The user's raw password
     * @return An encrypted cryptographic key to be used as database storage and authentication.
     */
    public static String encrypt(String password){
        return secureEncrypt(password.toCharArray());
    }

    /*
        Wrapper method hides the implementation from the public interface (caller)
            -Will handle the Exceptions thrown from the java.crypto classes.
            -Returns either an encrypted key or null
     */
    private static String secureEncrypt(char[] password){
        PBEKeySpec keySpec = new PBEKeySpec(password,CRYPTOSALT,ITERATIONS,KEY_SIZE);
        try{
            return generateSecureKey(keySpec);
        }catch(Throwable exception){
            System.err.println("Error in Authenticator.java: \nMessage: "+exception.getMessage()+"\nPrinting Stack Trace:\n");
            exception.printStackTrace();
            return null;
        }
    }

    private static String generateSecureKey(PBEKeySpec keySpec)throws NoSuchAlgorithmException, InvalidKeySpecException{
        SecretKeyFactory keyGen = SecretKeyFactory.getInstance(ALGORITHM);
        return Base64.getEncoder().encodeToString(keyGen.generateSecret(keySpec).getEncoded());
    }
}