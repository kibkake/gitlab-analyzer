package DatabaseClasses.config;

import DatabaseClasses.repository.ProjectRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@Configuration
public class MongoDBConfig {


}
