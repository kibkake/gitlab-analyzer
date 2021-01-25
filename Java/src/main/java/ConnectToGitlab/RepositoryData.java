package main.java.ConnectToGitlab;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;

import java.io.IOException;
import java.util.List;

public class RepositoryData {

    static List<GitlabProject> gitlabMembershipProjects;
    static List<GitProject> gitProjects;

    //Get projects that user is a member of
    public static void collectMembershipProjects(GitlabAPI api) throws IOException {
       gitlabMembershipProjects = api.getMembershipProjects();
    }

    //
    public static void createGitProjects(){
        for(int i = 0; i < gitlabMembershipProjects.size(); i++){
            GitProject gitProject = new GitProject();
        }
    }


}
