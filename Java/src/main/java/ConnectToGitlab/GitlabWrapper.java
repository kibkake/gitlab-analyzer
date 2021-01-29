package main.java.ConnectToGitlab;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitlabWrapper {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";


    public static HttpURLConnection makeConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection;

    }

}
