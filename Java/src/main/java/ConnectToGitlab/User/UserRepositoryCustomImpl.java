package main.java.ConnectToGitlab.User;

import main.java.DatabaseClasses.DatabaseFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Works with the database functions written for the Java MongoDB driver and is used in the UserRepository using the
 * naming conventions spring requires for the interfaces and classes allowing us to keep the functions already worked on
 * and continue with springs built in functions. This uses the mongoTemplate to connect to spring with through
 * autowiring.
 */

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
