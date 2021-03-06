package main.java.DatabaseClasses.Repository.Commit;

import main.java.Collections.Commit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implements basic CRUD and adds use for custom mongoDB functions for use with spring.
 * The MongoRepository implements the basic CRUD and is connected to spring through the config file using the mongo
 * template bean. CommitRepositoryCustom follows the spring naming conventions to implement custom database function with
 * the custom interface and its implementation
 */

@Repository
public interface CommitRepository extends MongoRepository<Commit, String>, CommitRepositoryCustom {

    //TODO update to Author_Email when that issue be solved
    List<Commit> findByProjectIdAndAndAuthorNameAndDateBetween(int projectId, String authorName, LocalDateTime start, LocalDateTime end);

    Commit findByProjectIdAndCommitId(int projectId, String commitHash);
}
