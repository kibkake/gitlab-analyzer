package main.java.Security;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public final class Authenticator{
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final byte[] CRYPTOSALT = ("gMsLe2L49faE23lS").getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final int KEY_SIZE = 512;
    private static final int ITERATIONS = 100000;

    public static String encrypt(String password){
        return secureEncrypt(password.toCharArray());
    }

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