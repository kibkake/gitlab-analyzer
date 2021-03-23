package main.java.DatabaseClasses.Repository.Developer;

import main.java.Collections.Developer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Implements basic CRUD and adds use for custom mongoDB functions for use with spring.
 * The MongoRepository implements the basic CRUD and is connected to spring through the config file using the mongo
 * template bean. DeveloperRepositoryCustom follows the spring naming conventions to implement custom database function with
 * the custom interface and its implementation
 */
@Repository
public interface DeveloperRepository extends MongoRepository<Developer, Integer>, DeveloperRepositoryCustom {

    @Query(fields="{ 'id' : 1, 'projectId' : 1, 'name' : 1, 'username' :1 }") // UI only wants these fields
    List<Developer> findDevelopersByProjectId(int projectId);

    Developer findDeveloperByProjectIdAndDevId(int projectId, int devId);

}
