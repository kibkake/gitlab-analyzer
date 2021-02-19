package main.java.ConnectToGitlab.apiwrapper;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ConnectToGitlab {

    public static void connectGitlab(String token) throws IOException, ParseException {
        //WrapperProject project = new WrapperProject(token, 6);
    }

    //Get Ids of all commits pushed by a specific user (commits in a merged branch)
    public static List<String> getUserMergeCommitIds(String username, List<GitlabCommit> gitlabCommits){
        //Check which commits from a merge request are from the current user
        List<String> ids = new ArrayList<>();
        for(int i = 0; i < gitlabCommits.size(); i++){
            if(gitlabCommits.get(0).getAuthorName().equals(username)){
                ids.add(gitlabCommits.get(i).getId());
            }
        }
        return ids;
    }

    //Get all commits in a project
    public static List<GitlabCommit> getAllGitlabCommits(GitlabAPI api, GitlabProject gitlabProject) throws IOException {
        return api.getAllCommits(gitlabProject.getId());
    }

    //Get commit diffs from 2 commits
    public static List<GitlabCommitDiff> getCommitDiffFromTwoCommits(GitlabAPI api, GitlabProject gitlabProject,  GitlabCommit oldGitlabCommit, GitlabCommit newGitlabCommit) throws IOException {
        return api.compareCommits(gitlabProject.getId(), oldGitlabCommit.getId(), newGitlabCommit.getId()).getDiffs();
    }

    //Get commit diffs from 2 commits (With string argument)
    public static List<GitlabCommitDiff> getCommitDiffFromTwoCommits(GitlabAPI api, GitlabProject gitlabProject,  GitlabCommit oldGitlabCommit, String newGitlabCommit) throws IOException {
        return api.compareCommits(gitlabProject.getId(),  oldGitlabCommit.getId(), newGitlabCommit).getDiffs();
    }

    //Get commit diffs between a commit (in a merge branch) and the merge commit in the same merge branch
    public static List<GitlabCommitDiff> getDiffFromCommitAndMergeCommit(GitlabAPI api, GitlabProject gitlabProject, GitlabCommit gitlabCommit) throws IOException {
        String mergeParentHash = getParentCommitHashOfMergeRequest(api, gitlabProject, gitlabCommit);
        return getCommitDiffFromTwoCommits(api, gitlabProject, gitlabCommit, mergeParentHash);
    }

    //Get all project issues
    public static List<GitlabIssue> getGitlabIssues(GitlabAPI api, GitlabProject gitlabProject){
        return api.getIssues(gitlabProject);
    }

    //Get all commits from a merge request
    public static List<GitlabCommit> getMergeCommits(GitlabAPI api, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        return api.getCommits(gitlabMergeRequest);
    }

    //Get changes from a merge request
    public static List<GitlabCommitDiff> getMergeRequestDiff(GitlabAPI api, GitlabProject gitLabProject, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        return api.getMergeRequestChanges(gitLabProject.getId(), gitlabMergeRequest.getIid()).getChanges();
    }

    //Get the merge branch parent commit hash from a commit in the same merge brancg
    public static String getParentCommitHashOfMergeRequest(GitlabAPI api, GitlabProject gitlabProject, GitlabCommit gitlabCommit) throws IOException {
        List <GitlabCommit> gitlabMasterCommits = api.getAllCommits(gitlabProject.getId(), "master");
        for(int i = 0; i < gitlabMasterCommits.size(); i++){
            List<String> gitParentHashes = gitlabMasterCommits.get(i).getParentIds();
            for(int j = 0; j < gitParentHashes.size(); j++){
                if(gitParentHashes.get(j).equals(gitlabCommit.getId())){
                    return gitlabMasterCommits.get(i).getId();
                }
            }
        }
        return null;
    }

    //Get name of a project
    public static String getProjectName(GitlabProject gitlabProject){
        return gitlabProject.getName();
    }

    //Get url of a project
    public static String getProjectUrl(GitlabProject gitlabProject){
        return gitlabProject.getHttpUrl();
    }

    //Get diff from a single commit
    public static List<GitlabCommitDiff> getSingleCommitDiff(GitlabAPI api, GitlabProject gitlabProject, GitlabCommit gitlabCommit) throws IOException {
        return api.getCommitDiffs(gitlabProject.getId(), gitlabCommit.getId());
    }

    //Retrieve project by id
    public static GitlabProject getSpecificProjectById(List<GitlabProject> gitlabProjects, int id){
        for (GitlabProject gitlabProject : gitlabProjects) {
            if (gitlabProject.getId().equals(id)) {
                return gitlabProject;
            }
        }
        return null;
    }

    //Retrieve project by name
    public static GitlabProject getSpecificProjectByName(List<GitlabProject> gitlabProjects, String projectName){
        for (GitlabProject gitlabProject : gitlabProjects) {
            if (gitlabProject.getName().equals(projectName)) {
                return gitlabProject;
            }
        }
        return null;
    }

    //Get all accessible projects
    public static List<GitlabProject> getUserAccessibleProjects(GitlabAPI api) throws IOException {
        return api.getProjects();
    }

    //Get user information (one who inputs access token)
    public static GitlabUser getUserFromApi(GitlabAPI api) throws IOException {
        return api.getUser();
    }

    //Get projects where user is a member of
    public static List<GitlabProject> getUserMemberProjects(GitlabAPI api) throws IOException {
        return api.getMembershipProjects();
    }

    //Get all merged merge requests
    public static List<GitlabMergeRequest> gitlabMergeRequests(GitlabAPI api, GitlabProject gitlabProject) throws IOException {
        assert (gitlabProject != null);
        return api.getMergedMergeRequests(api.getProject(gitlabProject.getId()));
    }

    //Find out if a user has commits in a merge branch
    public static boolean isUserPartOfMerge(GitlabAPI api, String username, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        List<GitlabCommit> gitlabMergeCommits = getMergeCommits(api, gitlabMergeRequest);
        for(int i = 0; i < gitlabMergeCommits.size(); i++){
            if(gitlabMergeCommits.get(i).getAuthorName().equals(username)){
                return true;
            }
        }
        return false;
    }

    //connec to server
    public static GitlabAPI makeConnectionToGitlab(String token){
        return GitlabAPI.connect("https://cmpt373-1211-10.cmpt.sfu.ca", token, TokenType.ACCESS_TOKEN, AuthMethod.URL_PARAMETER);
    }

    public static String printCommitMessage(GitlabCommit gitlabCommit){
        return gitlabCommit.getTitle();
    }

    public static void printCommitDiffs(List<GitlabCommitDiff> gitlabCommitDiffs) {
        for (int i = 0; i < gitlabCommitDiffs.size(); i++) {
            System.out.println(gitlabCommitDiffs.get(i).getNewPath() + ":  ");
            System.out.println(gitlabCommitDiffs.get(i).getDiff());
        }
    }

    public static void printMembershipProjects(List<GitlabProject> gitlabProjects){
        System.out.println("List of projects:");
        for (int i = 0; i < gitlabProjects.size(); i++) {
            System.out.print(i + 1 + ". " + gitlabProjects.get(i).getName());
            System.out.println("  " + gitlabProjects.get(i).getDefaultBranch());
        }
        System.out.println();
    }

    public static void printMergeRequestChanges(List<GitlabCommitDiff> gitlabCommitDiffs){
        for (int i = 0; i < gitlabCommitDiffs.size(); i++) {
            System.out.println(gitlabCommitDiffs.get(i).getDiff());
        }
        System.out.println();
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

    //To generate output on terminal and manually test functionality
    public static void runUserRetrieval(GitlabAPI api, GitlabProject gitlabProject) throws IOException {
        RepositoryData.collectMembershipProjects(api);
        RepositoryData.searchForProjectByName("Testproject2");
        List<GitlabMergeRequest> userMergeRequests = RepositoryData.getUserMergeRequests(api, "arahilin");
        List<GitlabCommit> userMergeCommits = RepositoryData.getUserMergeCommits(api, "arahilin");
        for(int i = 0; i< userMergeCommits.size(); i++){
            System.out.print(userMergeCommits.get(i).getTitle() + ":  ");
            List<GitlabCommitDiff> userGitlabCommitDiffs = getSingleCommitDiff(api, gitlabProject, userMergeCommits.get(i));
            printCommitDiffs(userGitlabCommitDiffs);
            System.out.println(RepositoryData.calculateCommitScoreTotal(userGitlabCommitDiffs));
        }
    }

    //Turn commit diff into String array
    public static void turnCommitDiffsIntoString(GitlabAPI api, GitlabProject gitlabProject, GitlabCommit newergitlabCommi, GitlabCommit oldergitlabCommit) throws IOException {
             //Get the commit diffs between two specific commits (newest and second newest)
            List<GitlabCommitDiff> gitlabCommitDiffsFromCommits = getCommitDiffFromTwoCommits(api, gitlabProject,oldergitlabCommit, newergitlabCommi);
            String [] commitDiffStringTwoCommit = new String[gitlabCommitDiffsFromCommits.size()];
            for (int i = 0; i < gitlabCommitDiffsFromCommits.size(); i++) {
                commitDiffStringTwoCommit[i] = gitlabCommitDiffsFromCommits.get(i).getDiff();
            }
    }
}