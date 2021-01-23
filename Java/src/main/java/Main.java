package main.java;
import main.java.ConnectToGitlab.ConnectToGitlab;

import java.io.IOException;


public class Main {
    public static void main(String args[]) {
        // print to indicate which server we are using
        System.out.println("\nGitLab Instance: "+ConnectToGitlab.GITLAB_URL);

        try {
            //Create an access token on your gitlab account and pass it in. Remove when pushing to avoid extra conflicts
            ConnectToGitlab.connectGitlab("");
            System.out.println("\nSuccessfully Connected to GitLab API with Access Token!");
        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }

    }
}
