package DatabaseClasses.repository;

import DatabaseClasses.model.Projects;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * This interface connects Spring app to MongoDB.
 * By this interface you can use the default methods of mongoDB,
 * save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…
 * in addition to our own defined methods
 */
//@Repository
//@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
//@Component
//@Repository
public interface ProjectRepository extends MongoRepository<Projects, Integer> {

    List<Projects> findByIdContaining(int PROJECT_ID);
    List<Projects> findByName(String PROJECT_NAME);
}