package main.java;
import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.UI.UI;
import main.java.DatabaseClasses.DatabaseFunctions;
import main.java.Functions.LocalDateFunctions;

import java.io.IOException;


public class Main {
    public static void main(String args[]) {
        try {
            ConnectToGitlab.connectGitlab("cFzzy7QFRvHzfHGpgrr1");//access token for the test server
        }catch(IOException exception){
            System.out.println("Problem in ConnectToGitLab:\n" + exception.getMessage());
        }

        try {
            UI.start_UI(args);
        } catch(Exception exception) {
            System.out.println("Problem in UI:\n" + exception.getMessage());
        }
    }
}
