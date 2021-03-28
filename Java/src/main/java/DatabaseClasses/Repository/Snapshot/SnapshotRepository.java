package main.java.DatabaseClasses.Repository.Snapshot;

import main.java.Collections.Snapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
 This interface connects Spring app to MongoDB.

 By this interface you can use the default methods of mongoDB,
 save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…
 without implementing these methods, in addition to our own defined methods
 */
@Repository
public interface SnapshotRepository extends MongoRepository <Snapshot, String> {
    public Snapshot findByUsername(String username);

    @Query(fields="{ 'username' : 1, 'startDate' : 1, 'endDate' : 1, 'projectId' :1 ,'dev' :1 }")
    List<Snapshot> getAllBy();
}