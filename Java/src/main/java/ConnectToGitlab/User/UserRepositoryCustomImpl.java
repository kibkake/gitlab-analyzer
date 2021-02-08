package main.java.ConnectToGitlab.User;

import main.java.DatabaseClasses.DatabaseFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class UserRepositoryCustomImpl implements UserRepositoryCustom  {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createUserAccount(User user) {
        DatabaseFunctions.createUserAccount(user);
    }

    @Override
    public User retrieveUserInfo(String username) {
        return DatabaseFunctions.retrieveUserInfo(username);
    }


}
