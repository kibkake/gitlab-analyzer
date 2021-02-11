package main.java.ConnectToGitlab.Wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all merged merge request, commits, commit diffs,
 * and notes of a particular user. It needs the username of the username
 * and the project object to successfully create a user object.
 */
public class WrapperUser {

    private final String NAME;
    private final List<WrapperMergedMergeRequest> MERGED_MERGE_REQUESTS = new ArrayList<>();
    private final List<WrapperIssue> ALL_ISSUES = new ArrayList<>();
    private double commitScore = 0.0;
    private double issueScore = 0.0;

    public WrapperUser(String name, WrapperProject project) {
        this.NAME = name;
        getUserMergedMergeRequest(project);
        //getUserIssues(project);
        calculateUserCommitScore();
        //calculateUserIssueScore();
    }

    /**
     * Retrieves user's merged merge request: merged merge request that user
     * has atleast one commit in.
     * @param project the project object which includes all the necessary data about the repository.
     */
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
        removeMergeNotesFromOtherAuthors();
    }

    /**
     * Checks if a user has at least one commit in a merged merge request
     * has atleast one commit in.
     * @param project the project object which includes all the necessary data about the repository.
     * @param index the index number of the merge request that contains the commits.
     */
    private boolean checkUserIsPartOfMerge(WrapperProject project, int index) {
        for(int j = 0; j < project.getMergedMergeRequests().get(index).getMergeRequestCommits().size(); j++) {
            if(project.getMergedMergeRequests().get(index).getMergeRequestCommits().get(j).getAuthorName().equals(NAME)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a user has at least one commit in a merged merge request
     * has at least one note in.
     * @param project the project object which includes all the necessary data about the repository.
     * @param index the index number of the merge request that contains the notes.
     */
    private boolean checkUserHasNoteInMerge(WrapperProject project, int index) {
        for(int j = 0; j < project.getMergedMergeRequests().get(index).getNotes().size(); j++) {
            if(project.getMergedMergeRequests().get(index).getNotes().get(j).getAuthor().equals(NAME)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes commits that do not belong to the user.
     */
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

    /**
     * Removes notes that do not belong to the user.
     */
    private void removeMergeNotesFromOtherAuthors() {
        for(int i = 0; i < MERGED_MERGE_REQUESTS.size(); i++) {
            for(int j = 0; j < MERGED_MERGE_REQUESTS.get(i).getNotes().size(); j++) {
                if(!MERGED_MERGE_REQUESTS.get(i).getNotes().get(j).getAuthor().equals(NAME)) {
                    MERGED_MERGE_REQUESTS.get(i).removeNote(j);
                    j--;
                }
            }
        }
    }

    /**
     * Retrieves all issues where user has atleast one note in.
     * @param project the project object which includes all the necessary data about the repository.
     */
   /* private void getUserIssues(WrapperProject project) {
        boolean userIsPartOfIssue = false;
        for(int i = 0; i < project.getAllIssues().size(); i++){
            userIsPartOfIssue = checkUserIsPartOfIssue(project, i);
            if(userIsPartOfIssue) {
                ALL_ISSUES.add(project.getAllIssues().get(i));
            }
        }
        removeIssueNotesFromOtherAuthors();
    }*/

    /**
     * Checks if a user has atleast one note in an issue.
     * @param project the project object which includes all the necessary data about the repository.
     * @param index the index number of the issue that contains the notes.
     */
    /*private boolean checkUserIsPartOfIssue(WrapperProject project, int index) {
        for(int j = 0; j < project.getAllIssues().get(index).getNotes().size(); j++) {
            if(project.getAllIssues().get(index).getNotes().get(j).getAuthor().equals(NAME)) {
                return true;
            }
        }
        return false;
    }*/

    /**
     * Removes notes that do not belong to user.
     */
    /*private void removeIssueNotesFromOtherAuthors() {
        for(int i = 0; i < ALL_ISSUES.size(); i++) {
            for (int j = 0; j < ALL_ISSUES.get(i).getNotes().size(); j++) {
                if (!ALL_ISSUES.get(i).getNotes().get(j).getAuthor().equals(NAME)) {
                    ALL_ISSUES.get(i).removeNote(j);
                    j--;
                }
            }
        }
    }*/

    /**
     * Adds up and calculates the score from all user commits in the repository that are in merged
     * merge request. Also adds up all note scores from merged merge requests as well.
     */
    private void calculateUserCommitScore() {
        for(int i = 0; i < MERGED_MERGE_REQUESTS.size(); i++) {
            for (int j = 0; j < MERGED_MERGE_REQUESTS.get(i).getMergeRequestCommits().size(); j++) {
                commitScore += MERGED_MERGE_REQUESTS.get(i).getMergeRequestCommits().get(j).getCommitScore();
            }
            for (int k = 0; k < MERGED_MERGE_REQUESTS.get(i).getNotes().size(); k++) {
                issueScore += MERGED_MERGE_REQUESTS.get(i).getNotes().get(k).getScore();
            }
        }
    }

    /**
     * Adds up and calculates the score from all user notes in the repository that are in merged
     * merge request.
     */
    /*private void calculateUserIssueScore() {
        for(int i = 0; i < ALL_ISSUES.size(); i++) {
            for (int j = 0; j < ALL_ISSUES.get(i).getNotes().size(); j++) {
                issueScore += ALL_ISSUES.get(i).getNotes().get(j).getScore();
            }
        }
    }*/
}
