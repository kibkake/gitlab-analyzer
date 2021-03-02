package main.java.DatabaseClasses.Repository;

import main.java.Model.Commit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommitRepository extends MongoRepository<Commit, String>, CommitRepositoryCustom {

    //TODO update to Author_Email when that issue be solved
    List<Commit> findByProjectIdAndDateBetweenAndAuthorName(int projectId, Date start, Date end, String author_name);

    Commit findByProjectIdAndId(int projectId, String commitHash);

    @Query("SELECT AVG(u.age) from commits u")
    int getAverageAge();

}
