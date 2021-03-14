package main.java.DatabaseClasses.Repository;

import main.java.Model.Commit;
import main.java.Model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQueryRepository extends MongoRepository<Project, Integer> {

}
