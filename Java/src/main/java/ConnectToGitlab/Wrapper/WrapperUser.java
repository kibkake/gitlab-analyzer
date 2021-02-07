package main.java.ConnectToGitlab.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class WrapperUser {

    private final String NAME;
    private final List<WrapperMergedMergeRequest> MERGED_MERGE_REQUESTS = new ArrayList<>();
    private final List<WrapperIssue> ALL_ISSUES = new ArrayList<>();


    public WrapperUser(String name, WrapperProject project) {
        this.NAME = name;
        getUserMergedMergeRequest(project);
    }

    private void getUserMergedMergeRequest(WrapperProject project) {
        boolean userIsPartOfMerge = false;
        for(int i = 0; i < project.getMergedMergeRequests().size(); i++){
            userIsPartOfMerge = checkUserIsPartOfMerge(project, i);
            if(!userIsPartOfMerge){
                userIsPartOfMerge = checkUserHasNoteInMerge(project, i);
            }
            if(userIsPartOfMerge) {
                MERGED_MERGE_REQUESTS.add(project.getMergedMergeRequests().get(i));
            }
        }
        removeCommitsFromOtherAuthors();
    }

    private boolean checkUserIsPartOfMerge(WrapperProject project, int index) {
        for(int j = 0; j < project.getMergedMergeRequests().get(index).getMergeRequestCommits().size(); j++) {
            if(project.getMergedMergeRequests().get(index).getMergeRequestCommits().get(j).getAuthorName().equals(NAME)) {
                return true;
            }

        }
        return false;
    }

    private boolean checkUserHasNoteInMerge(WrapperProject project, int index) {
        for(int j = 0; j < project.getMergedMergeRequests().get(index).getNotes().size(); j++) {
            if(project.getMergedMergeRequests().get(index).getNotes().get(j).getAuthor().equals(NAME)) {
                return true;
            }
        }
        return false;
    }

    private void removeCommitsFromOtherAuthors() {
        for(int i = 0; i < MERGED_MERGE_REQUESTS.size(); i++) {
            for(int j = 0; j < MERGED_MERGE_REQUESTS.get(i).getMergeRequestCommits().size(); j++) {
                if(!MERGED_MERGE_REQUESTS.get(i).getMergeRequestCommits().get(j).getAuthorName().equals(NAME)){
                    MERGED_MERGE_REQUESTS.get(i).removeCommit(j);
                    j--;
                }
            }
        }

    }




}
