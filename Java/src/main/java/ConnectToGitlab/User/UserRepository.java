package main.java.ConnectToGitlab.User;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    void createUserAccount(User user);

    User retrieveUserInfo(String username);



}
