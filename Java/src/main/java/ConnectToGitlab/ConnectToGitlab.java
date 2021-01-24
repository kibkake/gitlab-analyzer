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

        GitlabAPI api = makeConnectionToGitlab(token);
        GitlabUser user = getUserFromApi(api);
        System.out.println("Welcome " + user.getName() + "!");

        //Get all the projects that user is a member of
        List<GitlabProject> projects = getUserMemberProjects(api);
        if(projects.size() == 0){
            System.out.println("No projects!");
            return;
        }

        //Print projects
        //printMembershipProjects(projects);

        //Get name and url of newest project
        GitlabProject gitlabProject = getSpecificProjectByName(projects, "TestProject");
        if(gitlabProject == null){
            System.out.println("No such project");
            return;
        }

        //Get a list of and print merge requests
        List<GitlabMergeRequest> gitlabMergeRequests = gitlabMergeRequests(api, gitlabProject);
        //printProjectMergeRequests(gitlabMergeRequests);

        //Exit if not merge requests to show
        if (gitlabMergeRequests.size() == 0) {
            System.out.println("No merge requests!");
            return;
        }

        //Get the changes from latest merge request
        List<GitlabCommitDiff> gitlabCommitDiffsFromMerge = getMergeRequestDiff(api, gitlabProject, gitlabMergeRequests.get(0));
        //printMergeRequestChanges(gitlabCommitDiffsFromMerge);

        //Get the title of the first commit of the first merge request
        List <GitlabCommit> gitlabCommitsFirstMerge = getMergeCommits(api, gitlabMergeRequests.get(0));
        if (gitlabCommitsFirstMerge.size() > 0) {
            //System.out.println(printCommitMessage(gitlabCommitsFirstMerge.get(gitlabCommitDiffsFromMerge.size()-1)));
        }

        //Get changes from the first commit of the first merge request
        if (gitlabCommitsFirstMerge.size() > 0) {
            System.out.println("First commit of first merge request changes: ");
            System.out.println(api.getCommitDiffs(projects.get(0).getId(), gitlabCommitsFirstMerge.get(0).getId()).get(0).getDiff());
        }
/*
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
        }*/
    }
    public static GitlabAPI makeConnectionToGitlab(String token){
        return GitlabAPI.connect("https://cmpt373-1211-10.cmpt.sfu.ca", token, TokenType.ACCESS_TOKEN, AuthMethod.URL_PARAMETER);
    }

    public static GitlabUser getUserFromApi(GitlabAPI api) throws IOException {
        return api.getUser();
    }

    public static List<GitlabProject> getUserMemberProjects(GitlabAPI api) throws IOException {
        return api.getMembershipProjects();
    }

    public static List<GitlabProject> getUserAccessibleProjects(GitlabAPI api) throws IOException {
        return api.getProjects();
    }

    public static void printMembershipProjects(List<GitlabProject> gitlabProjects){
        System.out.println("List of projects:");
        for (int i = 0; i < gitlabProjects.size(); i++) {
            System.out.print(i + 1 + ". " + gitlabProjects.get(i).getName());
            System.out.println("  " + gitlabProjects.get(i).getDefaultBranch());
        }
        System.out.println();
    }

    public static GitlabProject getSpecificProjectByName(List<GitlabProject> gitlabProjects, String projectName){
        for (GitlabProject gitlabProject : gitlabProjects) {
            if (gitlabProject.getName().equals(projectName)) {
                return gitlabProject;
            }
        }
        return null;
    }
    public static GitlabProject getSpecificProjectById(List<GitlabProject> gitlabProjects, int id){
        for (GitlabProject gitlabProject : gitlabProjects) {
            if (gitlabProject.getId().equals(id)) {
                return gitlabProject;
            }
        }
        return null;
    }

    public static String getProjectUrl(GitlabProject gitlabProject){
        return gitlabProject.getHttpUrl();
    }

    public static String getProjectName(GitlabProject gitlabProject){
        return gitlabProject.getName();
    }

    public static List<GitlabMergeRequest> gitlabMergeRequests(GitlabAPI api, GitlabProject gitlabProject) throws IOException {
        assert (gitlabProject != null);
        return api.getMergeRequests(api.getProject(gitlabProject.getId()));
    }

    public static void printProjectMergeRequests(List<GitlabMergeRequest> gitlabMergeRequests){
        if(gitlabMergeRequests.size() == 0){
            return;
        }
        System.out.println("List of merge requests:");
        for (int i = 0; i < gitlabMergeRequests.size(); i++) {
            System.out.print(i + 1 + ". " + gitlabMergeRequests.get(i).getTitle() + "   ");
        }
        System.out.println("\n");
    }

    public static List<GitlabCommitDiff> getMergeRequestDiff(GitlabAPI api, GitlabProject gitLabProject, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        return api.getMergeRequestChanges(gitLabProject.getId(), gitlabMergeRequest.getIid()).getChanges();
    }

    public static void printMergeRequestChanges(List<GitlabCommitDiff> gitlabCommitDiffs){
        for (int i = 0; i < gitlabCommitDiffs.size(); i++) {
            System.out.println(gitlabCommitDiffs.get(i).getDiff());
        }
        System.out.println();
    }

    public static List<GitlabCommit> getAllGitlabCommits(GitlabAPI api, GitlabProject gitlabProject) throws IOException {
        return api.getAllCommits(gitlabProject.getId());
    }

    public static List<GitlabCommit> getMergeCommits(GitlabAPI api, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        return api.getCommits(gitlabMergeRequest);
    }

    public static String printCommitMessage(GitlabCommit gitlabCommit){
        return gitlabCommit.getTitle();
    }

}