package main.java.ConnectToGitlab.Commit;

import main.java.DatabaseClasses.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository  extends MongoRepository<Commit, String> {

}
