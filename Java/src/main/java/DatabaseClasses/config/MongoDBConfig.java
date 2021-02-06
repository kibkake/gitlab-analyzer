package DatabaseClasses.config;

import DatabaseClasses.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//[https://www.youtube.com/watch?v=l5KC6OcbuOI]
@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProjectRepository projectRepository) {
        return strings -> {
            // only if the db is empty, call the data via gitlab api.
            // otherwise the data is already in the db, fetch data from the db
            if (projectRepository.findAll().isEmpty()) {
            }

//            projectRepository.save(GitLabClient.getProjects());


            // save methods for other data from api could be added more
        };
    }

}
