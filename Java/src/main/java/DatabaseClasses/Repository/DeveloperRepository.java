package main.java.DatabaseClasses.Repository;

import main.java.Model.Developer;
import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends MongoRepository<Developer, Integer>, DeveloperRepositoryCustom {

    @Query(fields="{ 'id' : 1, 'projectId' : 1, 'name' : 1, 'username' :1 }") // UI only wants these fields
    List<Developer> findDevelopersByProjectId(int projectId);

    Developer findDeveloperByProjectIdAndDevId(int projectId, int devId);

}
