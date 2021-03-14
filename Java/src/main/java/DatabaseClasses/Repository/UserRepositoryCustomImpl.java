package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.DatabaseFunctions;
import main.java.Model.User;
import main.java.Model.ProjectSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;


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

    @Override
    public ProjectSettings retrieveUserSettings(String username, String settingName, int projectId) {
        final Criteria nameMatchCriteria = Criteria.where("username").is(username);
        final Criteria settingNameMatch = Criteria.where("ProjectSettings.name").is(settingName);
        final Criteria projectIdMatch = Criteria.where("ProjectSettings.projectId").is(projectId);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, settingNameMatch, projectIdMatch);

        Query query = new Query();
        query.addCriteria(criterias);
        query.fields().include("name").position("ProjectSettings", 1);
        return mongoTemplate.findOne(query, ProjectSettings.class);
    }
    @Override
    public void addSetting(String username, ProjectSettings setting) {
        User user = DatabaseFunctions.retrieveUserInfo(username);
        List<ProjectSettings> settings = user.getProjectSettings();
        settings.add(setting);
        Update update = new Update();
        update.set("projectSettings", settings);

        final Criteria nameMatchCriteria = Criteria.where("username").is(username);
        Query query = new Query();
        query.addCriteria(nameMatchCriteria);
        mongoTemplate.updateFirst(query, update, User.class);

    }


}
