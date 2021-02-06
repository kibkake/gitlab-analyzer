package main.java.ScoreLogic;

import java.time.LocalDate;
import java.util.List;

/**
 * This class manages functions as to the calculation of comment contribution on code review/issues,
 * by word count and frequency.
 * (For now, this class is written by assumption the required data is saved at DB,
 * but the way to access to DB is not clear yet so consider it as dummy code.)
 */
public class CommentContribution {
    /*
    required API: Notes on Issues & merge request [https://docs.gitlab.com/ee/api/notes.html]
    get all notes of a single merge request/Issue
    [/projects/:id/merge_requests/:merge_request_iid/notes], [/projects/:id/issues/:issue_iid/notes]
    then filter by author id/username/body/created_at etcs.
    */

    private int authorID;
    private int numOfComments;
    private int commentsPerDay;
    private List<String> commentsOnIssue;
    private List<String> commentsOnMergeRequest;
    private int numWordsOfCommentsOnIssue;
    private int numWordsOfCommentsOnMergeRequest;

    //fetch the whole data from db somehow
    public void getCommentsFromDB (int authorID) {
        // this.commentsOnIssue =
        // this.commentsOnMergeRequest =
    }

    //fetch the data for a date range from db somehow
    //there should be a way to fetch data sorted by date, via some DB configuration
    public void getCommentsByDateFromDB (int authorID) {
        // this.commentsOnIssue =
        // this.commentsOnMergeRequest =
    }

    public void getFrequencyOfComments(LocalDate startDate, LocalDate endDate) {
        this.numOfComments = commentsOnIssue.size() + commentsOnMergeRequest.size();
        //this.commentsPerDay =  numOfComments /  the numberOfDays during the date range
    }

    public void setNumWordsOfComments (int authorID) {
        this.numWordsOfCommentsOnIssue = countWords(commentsOnIssue);
        this.numWordsOfCommentsOnMergeRequest = countWords(commentsOnMergeRequest);
    }

    public int countWords (List<String> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (String comment : comments) {
            String[] splitComments = comment.split("\\s+");
            count =+ splitComments.length;
        }

        return count;
    }


    public void saveNumWordsOfComments () {
        //save the data to the DB, there should be attributes of the two variables for each author
    }

    public void saveFrequencyOfComments () {
        //save the data to the DB, there should be an attribute of the variables for each author
    }
}
