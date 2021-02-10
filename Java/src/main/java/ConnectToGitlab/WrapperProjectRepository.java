package main.java.ConnectToGitlab;

import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.DatabaseClasses.Model.Commit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WrapperProjectRepository extends MongoRepository<WrapperProject, Integer> {

}
