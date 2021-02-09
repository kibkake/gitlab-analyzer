package main.java.DatabaseClasses.config;

import main.java.DatabaseClasses.repository.ProjectRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@Configuration
public class MongoDBConfig {


    // This is a suggestion, if we set bean for this command lineRunner and
    // Main implements commandline runner, we can hide all db functions in this class.
    // Since current Main different structure, I didn't change it


    // @Bean
//    CommandLineRunner commandLineRunner(ProjectRepository projectRepository) {
//        return strings -> {

//            projectRepository.save(projects);

//            // save, and other methods for other data from api could be added more
//        };
//    }

}
