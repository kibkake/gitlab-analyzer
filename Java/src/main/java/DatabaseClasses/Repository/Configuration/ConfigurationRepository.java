package main.java.DatabaseClasses.Repository.Configuration;

import main.java.Collections.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<Configuration,String> {



}
