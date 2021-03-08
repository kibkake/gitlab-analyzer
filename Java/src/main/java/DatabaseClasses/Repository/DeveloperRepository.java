package main.java.DatabaseClasses.Repository;

import main.java.Model.Developer;
import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends MongoRepository<Developer, Integer> {

    Developer findDeveloperByDevIdAndProjectId(int devId, int projectId);

}
