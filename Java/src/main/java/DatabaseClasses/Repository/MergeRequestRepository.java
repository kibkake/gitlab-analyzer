package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MergeRequestRepository extends MongoRepository<MergeRequest, Integer>, MergeRequestRepositoryCustom {

}
