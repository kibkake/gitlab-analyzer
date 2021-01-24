package main.java.ConnectToGitlab;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.Pagination;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.*;
import org.gitlab.api.models.GitlabMergeRequest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        List<GitlabProjectMember> membersOfOneProject = api.getProjectMembers(gitlabProject);
        //System.out.println(membersOfOneProject.get(0).getUsername() + ", " + membersOfOneProject.get(0).getName());

        //Get a list of and print merge requests
        List<GitlabMergeRequest> gitlabMergeRequests = gitlabMergeRequests(api, gitlabProject);
        //printProjectMergeRequests(gitlabMergeRequests);

        //Exit if not merge requests to show
        if (gitlabMergeRequests.size() == 0) {
            System.out.println("No merge requests!");
            return;
        }

        //Get the changes from latest merge request
        List<GitlabCommitDiff> gitlabCommitDiffsFromMerge = getMergeRequestDiff(api, gitlabProject, gitlabMergeRequests.get(gitlabMergeRequests.size()-1));
        //printMergeRequestChanges(gitlabCommitDiffsFromMerge);

        //Get the title of the first commit of the first merge request
        List <GitlabCommit> gitlabCommitsFirstMerge = getMergeCommits(api, gitlabMergeRequests.get(gitlabMergeRequests.size()-1));
        if (gitlabCommitsFirstMerge.size() > 0) {
            //System.out.println(printCommitMessage(gitlabCommitsFirstMerge.get(gitlabCommitDiffsFromMerge.size()-1)));
        }

        //Get a list of a user's merge requests (where user has atleast one commit)
        List<GitlabMergeRequest> userGitlabMergeRequests = new ArrayList<>();
        for(int i = 0; i < gitlabMergeRequests.size(); i++){
            if(isUserPartOfMerge(api, "arahilin", gitlabMergeRequests.get(i))){
                userGitlabMergeRequests.add(gitlabMergeRequests.get(i));
            }
        }

        //Get changes from the first commit of the first merge request
        List<GitlabCommitDiff> gitlabCommitDiffsSingleCommit = getSingleCommitDiff(api, gitlabProject, gitlabCommitsFirstMerge.get(gitlabCommitsFirstMerge.size()-1));
        String [] commitDiffStringSingleCommit = new String[gitlabCommitDiffsSingleCommit.size()];

        for(int i = 0; i < gitlabCommitDiffsSingleCommit.size(); i++){
            commitDiffStringSingleCommit[i] = gitlabCommitDiffsSingleCommit.get(i).getDiff();
            //System.out.println(gitlabCommitDiffsSingleCommit.get(i).getNewPath());
            //System.out.println(commitDiffStringSingleCommit[i]);
            //System.out.println(calculateCommitScoreSingleDiff(gitlabCommitDiffsSingleCommit.get(i)));
        }
        //System.out.println(calculateCommitScoreTotal(gitlabCommitDiffsSingleCommit));

        //Get the commit diffs between two specific commits (newest and second newest)
        if (gitlabCommitsFirstMerge.size() > 1) {
            List<GitlabCommitDiff> gitlabCommitDiffsFromCommits = getCommitDiffFromTwoCommits(api, gitlabProject,gitlabCommitsFirstMerge.get(1), gitlabCommitsFirstMerge.get(0));
            String [] commitDiffStringTwoCommit = new String[gitlabCommitDiffsFromCommits.size()];
            for (int i = 0; i < gitlabCommitDiffsFromCommits.size(); i++) {
                commitDiffStringTwoCommit[i] = gitlabCommitDiffsFromCommits.get(i).getDiff();
            }
        }

        //Get issue titles
        List <GitlabIssue> gitlabIssues = getGitlabIssues(api, gitlabProject);
        String [] gitlabIssuesTitles = new String[gitlabIssues.size()];
        for (int i = 0; i < gitlabIssues.size(); i++) {
            gitlabIssuesTitles[i] = gitlabIssues.get(i).getTitle();
        }

        //Check which commits from a merge request are from the current user
        for(int i = 0; i < gitlabCommitsFirstMerge.size(); i++){
            if(gitlabCommitsFirstMerge.get(0).getAuthorName().equals(user.getName())){
                System.out.println("commit " + gitlabCommitsFirstMerge.get(i).getId() + "  belongs to current user");
            }
        }

        //Get parent commit of a merge request
        String mergeParentHash = getParentCommitHashOfMergeRequest(api, gitlabProject, gitlabCommitsFirstMerge.get(0));
        List<GitlabCommitDiff> gitlabCommitDiffCommitAndMergeCommit = getCommitDiffFromTwoCommits(api, gitlabProject, gitlabCommitsFirstMerge.get(gitlabCommitsFirstMerge.size()-1), mergeParentHash);
        String [] commitDiffCommitAndMerge = new String[gitlabCommitDiffCommitAndMergeCommit.size()];

        for(int i = 0; i < gitlabCommitDiffCommitAndMergeCommit.size(); i++){
            commitDiffCommitAndMerge [i] = gitlabCommitDiffCommitAndMergeCommit.get(i).getDiff();
        }


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

    public static List<GitlabCommitDiff> getSingleCommitDiff(GitlabAPI api, GitlabProject gitlabProject, GitlabCommit gitlabCommit) throws IOException {
        return api.getCommitDiffs(gitlabProject.getId(), gitlabCommit.getId());
    }

    public static List<GitlabCommitDiff> getCommitDiffFromTwoCommits(GitlabAPI api, GitlabProject gitlabProject,  GitlabCommit oldGitlabCommit, GitlabCommit newGitlabCommit) throws IOException {
        return api.compareCommits(gitlabProject.getId(), oldGitlabCommit.getId(), newGitlabCommit.getId()).getDiffs();
    }

    public static List<GitlabCommitDiff> getCommitDiffFromTwoCommits(GitlabAPI api, GitlabProject gitlabProject,  GitlabCommit oldGitlabCommit, String newGitlabCommit) throws IOException {
        return api.compareCommits(gitlabProject.getId(),  oldGitlabCommit.getId(), newGitlabCommit).getDiffs();
    }

    public static List<GitlabIssue> getGitlabIssues(GitlabAPI api, GitlabProject gitlabProject){
        return api.getIssues(gitlabProject);
    }

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

    public static boolean isUserPartOfMerge(GitlabAPI api, String username, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        List<GitlabCommit> gitlabMergeCommits = getMergeCommits(api, gitlabMergeRequest);
        for(int i = 0; i < gitlabMergeCommits.size(); i++){
            if(gitlabMergeCommits.get(i).getAuthorName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public static double calculateCommitScoreSingleDiff(GitlabCommitDiff gitlabCommitDiff){
        double score = 0.0;
        for(int j = 0; j < gitlabCommitDiff.getDiff().length(); j++){
            if(gitlabCommitDiff.getDiff().charAt(j) == '\n' && j < gitlabCommitDiff.getDiff().length()-2){
                j++;
                if(gitlabCommitDiff.getDiff().charAt(j) == '+'){
                    j++;
                    while(gitlabCommitDiff.getDiff().charAt(j) == ' ' || gitlabCommitDiff.getDiff().charAt(j) == '\t'){
                        j++;
                    }
                    if(gitlabCommitDiff.getDiff().charAt(j) == '\n'){
                        j--;
                    }else if(gitlabCommitDiff.getDiff().charAt(j) == '/'){
                        score += 0.0;

                    }else if(gitlabCommitDiff.getDiff().charAt(j) == '}' || gitlabCommitDiff.getDiff().charAt(j) == '{'){
                        score += 0.2;
                    }else{
                        score += 1.0;
                    }
                }
                if(gitlabCommitDiff.getDiff().charAt(j) == '-'){
                    score += 0.2;
                }
            }
        }
        return Math.round(score * 100.0) / 100.0;
    }

    public static double calculateCommitScoreTotal(List <GitlabCommitDiff> gitlabCommitDiffs){
        double score = 0.0;
        for(int i = 0; i < gitlabCommitDiffs.size(); i++) {
            score += calculateCommitScoreSingleDiff(gitlabCommitDiffs.get(i));
        }
        return Math.round(score * 100.0) / 100.0;
    }

}