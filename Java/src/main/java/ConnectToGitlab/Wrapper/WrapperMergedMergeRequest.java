package main.java.ConnectToGitlab.Wrapper;

import java.util.List;

public class WrapperMergedMergeRequest {

    private int mergeRequestId;
    private int mergeRequestIid;
    private int gitlabProjectId;
    private String mergeRequestTitle;
    private int mergeYear;
    private int mergeMonth;
    private int mergeDay;
    private List<WrapperCommit> mergeRequestCommits;

    public WrapperMergedMergeRequest(int mergeRequestId, int mergeRequestIid, int gitlabProjectId, String mergeRequestTitle,
                                     int mergeYear, int mergeMonth, int mergeDay) {
        this.mergeRequestId = mergeRequestId;
        this.mergeRequestIid = mergeRequestIid;
        this.gitlabProjectId = gitlabProjectId;
        this.mergeRequestTitle = mergeRequestTitle;
        this.mergeYear = mergeYear;
        this.mergeMonth = mergeMonth;
        this.mergeDay = mergeDay;
        //this.mergeRequestCommits = mergeRequestCommits;
    }
}
