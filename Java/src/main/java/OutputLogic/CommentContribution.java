package main.java.OutputData;

import java.util.List;

/*
    This class manages the calculation of comment contribution on code review/issues, by word count and frequency.
    For now, this class is written by assumption the required data is saved at DB.
 */
public class CommentContribution {
    /*
    required API: Notes on Issues & merge request [https://docs.gitlab.com/ee/api/notes.html]
    get all notes of a single merge request/Issue
    [/projects/:id/merge_requests/:merge_request_iid/notes], [/projects/:id/issues/:issue_iid/notes]
    then filter by author id/username/body/created_at etcs.
    */

    private int authorID;
    private List<String> commentsOnIssue;
    private List<String> commentsOnMergeRequest;
    private int numWordsOfCommentsOnIssue;
    private int numWordsOfCommentsOnMergeRequest;

    //fetch data from db somehow
    public static void getCommentsFromDB (int authorID) {
        // this.commentsOnIssue =
        // this.commentsOnMergeRequest =
    }


    public void setNumWordsOfComments (int authorID) {
        this.numWordsOfCommentsOnIssue = countWords(commentsOnIssue);
        this.numWordsOfCommentsOnMergeRequest = countWords(commentsOnMergeRequest);
    }


    public static int countWords (List<String> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0;
        }

        String[] splitComments;
        int count = 0;

        for (String comment : comments) {
            splitComments = comment.split("\\s+");
            count =+ splitComments.length;
        }

        return count;
    }


    public void saveNumWordsOfComments () {
        //save the data to the DB, there should be attributes of the two variables for each author
    }

}
