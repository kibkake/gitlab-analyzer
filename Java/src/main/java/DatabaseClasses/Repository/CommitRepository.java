package main.java.DatabaseClasses.Repository;

import main.java.Model.Commit;
import main.java.Model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommitRepository extends MongoRepository<Commit, String> {

    //TODO update to Author_Email when that issue be solved
    List<Commit> findByProjectIdAndDateBetweenAndAuthorName(int projectId, Date start, Date end, String author_name);
}
