package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.List;

public interface CommitRepositoryCustom {

    List<CommitDateScore> getDevDateScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate);
}
