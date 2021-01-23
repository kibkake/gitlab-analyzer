package main.java;
import main.java.ConnectToGitlab.ConnectToGitlab;

import java.io.IOException;


public class Main {
    public static void main(String args[]) {
        // print to indicate which server we are using
        System.out.println("\nGitLab Instance: "+ConnectToGitlab.GITLAB_URL);

        try {
            // default access token for the test server
            ConnectToGitlab.connectGitlab("cFzzy7QFRvHzfHGpgrr1");
        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }

    }
}
