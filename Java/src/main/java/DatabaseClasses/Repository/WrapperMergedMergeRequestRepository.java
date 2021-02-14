package main.java.DatabaseClasses.Repository;

import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WrapperMergedMergeRequestRepository extends MongoRepository<WrapperMergedMergeRequest, Integer> {

    //public List<WrapperMergedMergeRequest> findByPROJECT_ID();
}
