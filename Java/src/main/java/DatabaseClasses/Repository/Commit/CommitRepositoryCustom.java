package main.java.DatabaseClasses.Repository.Commit;

import main.java.Collections.Commit;
import main.java.DatabaseClasses.Scores.CommitDateScore;

import java.time.LocalDate;
import java.util.List;

/**
 * Is used to help integrate the custom functions to aggregate data in the db using mongoTemplate
 */

public interface CommitRepositoryCustom {

    List<CommitDateScore> getDevCommitDateScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    Double userTotalCommitScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    List<CommitDateScore> getCommitsWithEveryDateBetweenRange(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

}
