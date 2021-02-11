package main.java.DatabaseClasses.repository;

import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WrapperMergedMergeRequestRepository extends MongoRepository<WrapperMergedMergeRequest, Integer> {

}
