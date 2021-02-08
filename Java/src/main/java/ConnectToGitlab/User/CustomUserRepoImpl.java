package main.java.ConnectToGitlab.User;

import main.java.DatabaseClasses.DatabaseFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomUserRepoImpl implements CustomUserRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomUserRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createUserAccount(String username, String password, String token) {
        DatabaseFunctions.createUserAccount(username, password, token);
    }
}
