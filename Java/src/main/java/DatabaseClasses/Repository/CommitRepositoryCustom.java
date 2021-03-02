package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;

import java.util.List;

public interface CommitRepositoryCustom {

    List<CommitDateScore> getDevDateScore(int projectId, String devUserName);
}
