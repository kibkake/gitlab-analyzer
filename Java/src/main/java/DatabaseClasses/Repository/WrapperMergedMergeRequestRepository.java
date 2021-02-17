package main.java.DatabaseClasses.Repository;

import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WrapperMergedMergeRequestRepository extends MongoRepository<WrapperMergedMergeRequest, Integer> {

    //public List<WrapperMergedMergeRequest> findByPROJECT_ID();
}
