package main.java.ConnectToGitlab;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.Pagination;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.*;
import org.gitlab.api.models.GitlabMergeRequest;


import java.io.IOException;
import java.util.List;

public class ConnectToGitlab {

    public static void connectGitlab(String token) throws IOException {
        GitlabAPI api = GitlabAPI.connect("https://cmpt373-1211-10.cmpt.sfu.ca", token, TokenType.ACCESS_TOKEN, AuthMethod.URL_PARAMETER);
        GitlabUser user = api.getUser();
        System.out.println("name: " + user.getName() + ".  email: " + user.getEmail());

        List<GitlabProject> projects = api.getMembershipProjects();
        if(projects.size() == 0){
            System.out.println("No projects!");
            return;
        }

        //List all projects
        System.out.println("List of projects:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.print(i + 1 + ". " + projects.get(i).getName());
            System.out.println("  " + projects.get(i).getDefaultBranch());
        }
        System.out.println();

        //Get name and url of newest project
        System.out.println("Newest project name : " + api.getProject(projects.get(0).getId()).getName());
        System.out.println("Newest project name : " + api.getProject(projects.get(0).getId()).getHttpUrl());
        System.out.println();

        //Get a list of merge requests
        System.out.println("List of merge requests:");
        List<GitlabMergeRequest> mergeRequests = api.getMergeRequests(api.getProject(projects.get(0).getId()));
        for (int i = 0; i < mergeRequests.size(); i++) {
            System.out.print(i + 1 + ". " + mergeRequests.get(i).getTitle() + "   ");
        }
        System.out.println("\n");

        //Exit if not merge requests to show
        if (mergeRequests.size() == 0) {
            System.out.println("No merge requests!");
            return;
        }

        //Get the changes from latest merge request
        System.out.println("Changes from latest marge request:");
        List<GitlabCommitDiff> gitlabCommitDiffsFromMerge = api.getMergeRequestChanges(projects.get(0).getId(), mergeRequests.get(0).getIid()).getChanges();
        for (int i = 0; i < gitlabCommitDiffsFromMerge.size(); i++) {
            System.out.println(gitlabCommitDiffsFromMerge.get(i).getDiff());
        }
        System.out.println();

        //Get the number of all commits from the project
        System.out.println("Number of TOTAL commits: " + api.getAllCommits(projects.get(0).getId()).size());

        //Get the title of the first commit of the first merge request
        List <GitlabCommit> gitlabCommitsFirstMerge = api.getCommits(mergeRequests.get(mergeRequests.size()-1));
        if (gitlabCommitsFirstMerge.size() > 0) {
            System.out.println("Commit message of the first commit of first merge request: " +
                    gitlabCommitsFirstMerge.get(api.getCommits(mergeRequests.get(mergeRequests.size() - 1)).size() - 1).getTitle());
        }

        //Get changes from the first commit of the first merge request
        if (gitlabCommitsFirstMerge.size() > 0) {
            System.out.println("First commit of first merge request changes: ");
            System.out.println(api.getCommitDiffs(projects.get(0).getId(), gitlabCommitsFirstMerge.get(0).getId()).get(0).getDiff());
        }

        //Get the commit diffs between two specific commits (newest and second newest)
        System.out.println("commit diff between first and second commit of first merge request:");
        if (gitlabCommitsFirstMerge.size() > 1) {
            List<GitlabCommitDiff> gitlabCommitDiffsFromCommits = api.compareCommits(projects.get(0).getId(),gitlabCommitsFirstMerge.get(1).getId(), gitlabCommitsFirstMerge.get(0).getId()).getDiffs();

            for (int i = 0; i < gitlabCommitDiffsFromCommits.size(); i++) {
                System.out.println(gitlabCommitDiffsFromCommits.get(i).getDiff());
            }
        }
        System.out.println();

        //Get issue titles
        List <GitlabIssue> gitlabIssues = api.getIssues(projects.get(0).getId());
        for (int i = 0; i < gitlabIssues.size(); i++) {
            System.out.println(gitlabIssues.get(i).getTitle());
        }

        //Check which commits from a merge request are from the current user
        for(int i = 0; i < gitlabCommitsFirstMerge.size(); i++){
            if(gitlabCommitsFirstMerge.get(0).getAuthorName().equals(user.getName())){
                System.out.println("commit " + gitlabCommitsFirstMerge.get(i).getId() + "  belongs to current user");
            }
        }


    }
}