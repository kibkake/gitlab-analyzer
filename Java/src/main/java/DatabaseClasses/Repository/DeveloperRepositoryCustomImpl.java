package main.java.DatabaseClasses.Repository;

import main.java.Model.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class DeveloperRepositoryCustomImpl implements DeveloperRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DeveloperRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveDev(Developer dev) {
        mongoTemplate.save(dev);
    }

}
