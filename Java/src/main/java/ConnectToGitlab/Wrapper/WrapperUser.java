package main.java.ConnectToGitlab.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class WrapperUser {

    private final String NAME;
    private final List<WrapperMergedMergeRequest> MERGED_MERGE_REQUESTS = new ArrayList<>();
    private final List<WrapperIssue> ALL_ISSUES = new ArrayList<>();


    public WrapperUser(String name, WrapperProject project) {
        this.NAME = name;
    }



}
