package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.Model.MergeRequest;

import java.time.LocalDate;
import java.util.List;

/*** Is needed to follow the spring naming conventions and help implement aggregation for getting a user scores.
 *
 */
public interface MergeRequestRepositoryCustom {

    //https://stackoverflow.com/questions/19583540/spring-data-jpa-no-property-found-for-type-exception
    // Spring tries to auto generate quires based of fucntion name =, nned to mkae sure we are not following any of these
    // when creating a custom function
    List<MergeRequestDateScore> getDevsMrsScoreADay(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    List<MergeRequest> getDevMergeRequests(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    Double getUserTotalMergeRequestScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    MergeRequest getMrByCommitHash(int projectId, String hash);

}
