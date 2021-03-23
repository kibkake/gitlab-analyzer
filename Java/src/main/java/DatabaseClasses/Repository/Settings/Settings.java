package main.java.DatabaseClasses.Repository.Settings;

import main.java.Collections.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Settings extends MongoRepository<Project, Integer> {

}
