package main.java.DatabaseClasses.Repository.Project;

import main.java.Collections.Commit;
import main.java.Collections.Project;
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
public interface ProjectRepository extends MongoRepository <Project, Integer> {

    Project findProjectById(int id);


    @Query(fields="{ 'id' : 1, 'description' : 1, 'name' : 1, 'createdAt' :1 }")
    List<Project> getAllBy();

}
