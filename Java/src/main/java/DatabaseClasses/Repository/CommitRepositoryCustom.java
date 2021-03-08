package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;

import java.time.LocalDate;
import java.util.List;

public interface CommitRepositoryCustom {

    List<CommitDateScore> getDevDateScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    Object userTotalCommitScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

}
