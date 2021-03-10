package main.java.DatabaseClasses.Repository;

import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MergeRequestRepository extends MongoRepository<MergeRequest, String>, MergeRequestRepositoryCustom {

    MergeRequest findByProjectIdAndId(int projectId, int id);

    List<MergeRequest> findByProjectId(int projectId);

}
