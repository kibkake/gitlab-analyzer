package main.java.DatabaseClasses.Repository.Snapshot;

import main.java.Collections.Snapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SnapshotRepository extends MongoRepository <Snapshot, String> {
    public Snapshot findByName(String name);
}