package main.java.DatabaseClasses.Repository;


import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WrapperProjectRepository extends MongoRepository<WrapperProject, Integer> {

}
