package main.java.DatabaseClasses.repository;

import main.java.ConnectToGitlab.Wrapper.WrapperCommit;
import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface WrapperCommitRepository extends MongoRepository<WrapperCommit, Integer> {

}


