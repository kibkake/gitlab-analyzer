package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.DatabaseFunctions;
import main.java.Model.User;
import main.java.Model.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;

/**
 * Works with the database functions written for the Java MongoDB driver and is used in the UserRepository using the
 * naming conventions spring requires for the interfaces and classes allowing us to keep the functions already worked on
 * and continue with springs built in functions. This uses the mongoTemplate to connect to spring with through
 * autowiring.
 */

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
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
    public void changePassword(User user){ DatabaseFunctions.changePassword(user);}

    @Override
    public void changeToken(User user){ DatabaseFunctions.changeToken(user);}

    @Override
    public void setToken(User user){
        User singleton_user = User.getInstance();
        String token = user.getToken();
        singleton_user.setToken(token);

        // debug
        User debug = User.getInstance();
        System.out.println("token set: "+debug.getToken());
    }

    @Override
    public User retrieveUserInfo(String username) {
        return DatabaseFunctions.retrieveUserInfo(username);
    }



}
