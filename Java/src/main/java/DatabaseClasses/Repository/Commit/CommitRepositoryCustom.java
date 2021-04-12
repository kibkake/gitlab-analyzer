package main.java.DatabaseClasses.Repository.Commit;

import main.java.DatabaseClasses.Scores.CommitDateScore;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Is used to help integrate the custom functions to aggregate data in the db using mongoTemplate
 */

public interface CommitRepositoryCustom {

    List<CommitDateScore> getDevCommitDateScore(int projectId, String devUserName, LocalDateTime startDate, LocalDateTime endDate);

    Double userTotalCommitScore(int projectId, String devUserName, LocalDateTime startDate, LocalDateTime endDate);

    List<CommitDateScore> getCommitsWithEveryDateBetweenRange(int projectId, String devUserName, LocalDateTime startDate, LocalDateTime endDate);
}
