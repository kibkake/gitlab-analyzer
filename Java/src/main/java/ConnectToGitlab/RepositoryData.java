package main.java.ConnectToGitlab;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryData {

    static List<GitlabProject> gitlabMembershipProjects;
    static List<GitProject> gitProjects;

    //Get projects that user is a member of
    public static void collectMembershipProjects(GitlabAPI api) throws IOException {
       gitlabMembershipProjects = api.getMembershipProjects();
    }

    //Create project objects
    public static void createGitProjects(GitlabAPI api) throws IOException {
        for(int i = 0; i < gitlabMembershipProjects.size(); i++){
            GitProject gitProject = new GitProject(gitlabMembershipProjects.get(i).getId());
            gitProject.gitProject = gitlabMembershipProjects.get(i);
            gitProject.gitlabProjectName = gitlabMembershipProjects.get(i).getName();
            gitProject.gitlabProjectMembers = api.getProjectMembers(gitlabMembershipProjects.get(i));
            gitProject.gitlabAllCommits = api.getAllCommits(gitlabMembershipProjects.get(i).getId());
            gitProject.gitAllIssues = api.getIssues(gitlabMembershipProjects.get(i));
            gitProject.gitlabMergedMergeRequests = api.getMergedMergeRequests(gitlabMembershipProjects.get(i));
            gitProjects.add(gitProject);
        }
    }

    public static GitProject searchForProjectByName(String name){
        for (GitProject gitProject : gitProjects) {
            if (gitProject.gitlabProjectName.equals(name)) {
                return gitProject;
            }
        }
        return null;
    }

    //Get a list of a user's merge requests (where user has atleast one commit)
    public static List<GitlabMergeRequest> getUserMergeRequests(GitlabAPI api, String username, List<GitlabMergeRequest> gitlabMergeRequests) throws IOException {
        List<GitlabMergeRequest> userGitlabMergeRequests = new ArrayList<>();
        for(int i = 0; i < gitlabMergeRequests.size(); i++){
            if(isUserPartOfMerge(api, username, gitlabMergeRequests.get(i))){
                userGitlabMergeRequests.add(gitlabMergeRequests.get(i));
                //System.out.println(gitlabMergeRequests.get(i).getTitle());
            }
        }
        return userGitlabMergeRequests;
    }

    public static List<GitlabCommit> getMergeCommits(GitlabAPI api, GitlabMergeRequest gitlabMergeRequest) throws IOException {
        return api.getCommits(gitlabMergeRequest);
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

}
