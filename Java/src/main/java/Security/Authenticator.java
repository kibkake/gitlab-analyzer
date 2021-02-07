package main.java.Security;

import java.security.SecureRandom;
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;

final class Authenticator{
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final byte[] CRYPTOSALT = ("gMsLe2L49faE23lS").getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final int KEY_SIZE = 512;

    public static String encrypt(String password){

    }

    private String secureEncrypt(char[] password){

    }
}