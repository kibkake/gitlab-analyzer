package main.java.ConnectToGitlab;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabMergeRequest;
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

        System.out.println(api.getProject(25591).getName());
        System.out.println(api.getProject(25591).getHttpUrl());


        List<GitlabMergeRequest> mergeRequests = api.getMergedMergeRequests(api.getProject(25591));
        for (int i = 0; i < mergeRequests.size(); i++) {
            System.out.print(i + 1 + ". " + mergeRequests.get(i).getTitle() + "   ");
        }
        System.out.println();

        System.out.print("100. " + mergeRequests.get(4).getTitle() + "   ");
        System.out.print("100. " + api.getCommits(api.getMergedMergeRequests(25591).get(4)).size());



        List<GitlabCommit> gitlabCommits = api.getCommits(api.getMergedMergeRequests(25591).get(4));
        for (int i = 0; i < gitlabCommits.size(); i++) {
            System.out.print(i + 1 + ". " + gitlabCommits.get(i).getCommittedDate() + "   ");
        }
        System.out.println();
        //System.out.println(hash.toString()gitlabCommits.get(0).hashCode());

        //List<GitlabCommitDiff> gitlabCommitDiffs = api.getCommitDiffs(25591, Integer.toString(gitlabCommits.get(0).hashCode()));
        //System.out.println(gitlabCommitDiffs.get(0).getDiff());


    }
}