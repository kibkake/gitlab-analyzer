package DatabaseClasses.repository;

import DatabaseClasses.model.Projects;
import org.springframework.data.mongodb.repository.MongoRepository;

/*
 This interface connects Spring app to MongoDB.

 By this interface you can use the default methods of mongoDB,
 save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…
 without implementing these methods, in addition to our own defined methods
 */
public interface ProjectRepository extends MongoRepository <Projects, Integer> {

}
