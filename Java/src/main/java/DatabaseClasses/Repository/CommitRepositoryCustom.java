package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;

import java.time.LocalDate;
import java.util.List;

public interface CommitRepositoryCustom {

    List<CommitDateScore> getDevCommitDateScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    Object userTotalCommitScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);

    List<CommitDateScore> getDevCommitArray(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);
}
