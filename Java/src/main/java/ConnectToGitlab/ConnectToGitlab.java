package main.java.ConnectToGitlab;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;

import java.io.IOException;
import java.util.List;

public class ConnectToGitlab {

    public static void connectGitlab(String token) throws IOException {
        GitlabAPI api = GitlabAPI.connect("https://csil-git1.cs.surrey.sfu.ca", token, TokenType.ACCESS_TOKEN, AuthMethod.URL_PARAMETER);
        GitlabUser user = api.getUser();
        System.out.println("name: " + user.getName() + ".  email: " + user.getEmail());

        List<GitlabProject> projects = api.getMembershipProjects();

        for (int i = 0; i < projects.size(); i++) {
            System.out.print(i + 1 + ". " + projects.get(i).getName());
            System.out.println("  " + projects.get(i).getDefaultBranch());
        }

    }
}