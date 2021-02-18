package main.java.DatabaseClasses.Repository;

import main.java.ConnectToGitlab.Wrapper.WrapperCommit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WrapperCommitRepository extends MongoRepository<WrapperCommit, Integer> {

    public WrapperCommit findByID(String id);

}


