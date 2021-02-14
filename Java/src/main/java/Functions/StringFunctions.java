package main.java.Functions;

import java.util.List;
import java.util.ArrayList;

public class StringFunctions {

    public static boolean inList(List<String> list, String element) {
        for (String current: list) {
            if (current.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
