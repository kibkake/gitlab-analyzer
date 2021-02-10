package main.java.ConnectToGitlab.Commit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommitRepository  extends MongoRepository<Commit, String> {
    List<Commit> findByDateBetween(Date from, Date to);

}
