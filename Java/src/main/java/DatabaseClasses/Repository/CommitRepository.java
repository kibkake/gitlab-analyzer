package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.Commit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Grants access to  mongo and sprig allowing us access to basic CRUD operations for database
 */
@Repository
public interface CommitRepository  extends MongoRepository<Commit, String> {
    List<Commit> findByDateBetweenAndProjectId(Date from, Date to, int projectId);

}
