package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.Model.Commit;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommitRepositoryCustom {

    List<DateScore> getDevDateScore(int projectId, String devUserName);
}
