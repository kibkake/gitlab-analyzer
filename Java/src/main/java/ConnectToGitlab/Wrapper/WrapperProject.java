package main.java.ConnectToGitlab.Wrapper;

import java.util.List;

public class WrapperProject {

    private int gitlabProjectId;
    private String gitlabProjectName;
    private List<WrapperMergedMergeRequest> mergedMergeRequests;
    private List<WrapperCommit> allCommits;

    public WrapperProject(int gitlabProjectId, String gitlabProjectName, List<WrapperMergedMergeRequest> mergedMergeRequests, List<WrapperCommit> allCommits) {
        this.gitlabProjectId = gitlabProjectId;
        this.gitlabProjectName = gitlabProjectName;
        this.mergedMergeRequests = mergedMergeRequests;
        this.allCommits = allCommits;
    }

    public int getGitlabProjectId() {
        return gitlabProjectId;
    }

    public String getGitlabProjectName() {
        return gitlabProjectName;
    }

    public List<WrapperMergedMergeRequest> getMergedMergeRequests() {
        return mergedMergeRequests;
    }

    public List<WrapperCommit> getAllCommits() {
        return allCommits;
    }

    public void setGitlabProjectId(int gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    public void setGitlabProjectName(String gitlabProjectName) {
        this.gitlabProjectName = gitlabProjectName;
    }

    public void setMergedMergeRequests(List<WrapperMergedMergeRequest> mergedMergeRequests) {
        this.mergedMergeRequests = mergedMergeRequests;
    }

    public void setAllCommits(List<WrapperCommit> allCommits) {
        this.allCommits = allCommits;
    }
}
