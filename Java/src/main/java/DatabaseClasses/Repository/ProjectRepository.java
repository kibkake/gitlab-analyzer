package main.java.DatabaseClasses.Repository;

import main.java.Model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 This interface connects Spring app to MongoDB.

 By this interface you can use the default methods of mongoDB,
 save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…
 without implementing these methods, in addition to our own defined methods
 */
@Repository
public interface ProjectRepository extends MongoRepository <Project, Integer> {

    Project findProjectById(int id);

}
