package main.java.ConnectToGitlab;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;

public class ConnectToGitlab {

    public static void connectGitlab(String token) {
        GitlabAPI api = GitlabAPI.connect("https://csil-git1.cs.surrey.sfu.ca", token, TokenType.ACCESS_TOKEN, AuthMethod.URL_PARAMETER);

    }
}