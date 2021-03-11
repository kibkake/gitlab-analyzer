package main.java.DatabaseClasses.Repository;

import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Implements basic CRUD and adds use for custom mongoDB functions for use with spring.
 * The MongoRepository implements the basic CRUD and is connected to spring through the config file using the mongo
 * template bean. MergeRequestRepositoryCustom follows the spring naming conventions to implement custom database
 * function with the custom interface and its implementation
 */
@Repository
public interface MergeRequestRepository extends MongoRepository<MergeRequest, String>, MergeRequestRepositoryCustom {

    MergeRequest findByProjectIdAndId(int projectId, int id);

    List<MergeRequest> findByProjectId(int projectId);

}
