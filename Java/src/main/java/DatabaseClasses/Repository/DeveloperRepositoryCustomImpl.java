package main.java.DatabaseClasses.Repository;

import main.java.Model.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
/**
 * There is a problem saving devs used mongotempalte to get around it.
 * Is used to help integrate the custom functions to aggregate data in the db using mongoTemplate
 */
public class DeveloperRepositoryCustomImpl implements DeveloperRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DeveloperRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // There is an issue saving a dev overriding and implementing basic method with contemplate
    @Override
    public void saveDev(Developer dev) {
        mongoTemplate.save(dev);
    }

}
