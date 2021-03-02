package main.java.DatabaseClasses.Repository;

import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MergeRequestRepository extends MongoRepository<MergeRequest, Integer>, MergeRequestRepositoryCustom {

}
