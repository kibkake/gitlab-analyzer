package main.java.ConnectToGitlab;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.Pagination;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.*;

import java.io.IOException;
import java.util.List;

public class ConnectToGitlab {

    public static void connectGitlab(String token) throws IOException {
        GitlabAPI api = GitlabAPI.connect("https://csil-git1.cs.surrey.sfu.ca", token, TokenType.ACCESS_TOKEN, AuthMethod.URL_PARAMETER);
        GitlabUser user = api.getUser();
        System.out.println("name: " + user.getName() + ".  email: " + user.getEmail());

        List<GitlabProject> projects = api.getMembershipProjects();

        //List all projects
        for (int i = 0; i < projects.size(); i++) {
            System.out.print(i + 1 + ". " + projects.get(i).getName());
            System.out.println("  " + projects.get(i).getDefaultBranch());
        }
        System.out.println();

        //Get name and url of newest project
        System.out.println(api.getProject(projects.get(0).getId()).getName());
        System.out.println(api.getProject(projects.get(0).getId()).getHttpUrl());

        //Get a list of merge requests
        List<GitlabMergeRequest> mergeRequests = api.getMergeRequests(api.getProject(projects.get(0).getId()));
        for (int i = 0; i < mergeRequests.size(); i++) {
            System.out.print(i + 1 + ". " + mergeRequests.get(i).getTitle() + "   ");
        }
        System.out.println("\n");

        //Get the changes from latest merge request
        System.out.println("Changes:  \n" + api.getMergeRequestChanges(projects.get(0).getId(), mergeRequests.get(0).getIid()).getChanges().get(0).getDiff().toUpperCase());
        System.out.println();

        //Get the number of all commits from the project
        System.out.print("Number of commits: " + api.getAllCommits(projects.get(0).getId()).size());

    }
}