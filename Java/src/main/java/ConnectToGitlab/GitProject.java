package main.java.ConnectToGitlab;

import org.gitlab.api.models.*;

import java.util.List;

public class GitProject {

    public int gitlabProjectId;
    public String gitlabProjectName;
    public GitlabProject gitProject;
    public List<GitlabProjectMember> gitlabProjectMembers;
    public List<GitlabMergeRequest> gitlabMergedMergeRequests;
    public List<GitlabCommit> gitlabAllCommits;
    public List<GitlabIssue> gitAllIssues;

    public GitProject(int gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

}
