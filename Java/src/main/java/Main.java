package main.java;
import main.java.ConnectToGitlab.ConnectToGitlab;

import java.io.IOException;


public class Main {
    public static void main(String args[]) {
        System.out.println("Hello");

        try {
            ConnectToGitlab.connectGitlab("cFzzy7QFRvHzfHGpgrr1");//access token for the test server
        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }

    }
}
