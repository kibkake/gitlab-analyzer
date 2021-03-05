package main.java.DatabaseClasses.Repository;

import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MergeRequestRepository extends MongoRepository<MergeRequest, Integer>, MergeRequestRepositoryCustom {

    List<MergeRequest> findByProjectIdAndAuthorUsernameAndMergedDateBetween(int projectId, String authorUsername, Date start, Date end);

    MergeRequest findByProjectIdAndIid(int projectId, int iid);

    List<MergeRequest> findByProjectId(int projectId);

}
