package main.java.DatabaseClasses.Repository.Snapshot;

import main.java.Collections.Snapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SnapshotRepository extends MongoRepository <Snapshot, String> {
    public Snapshot findByName(String name);

    @Query(fields="{ 'name' : 1, 'startDate' : 1, 'endDate' : 1, 'projectId' :1 }")
    List<Snapshot> getAllBy();
}