package DatabaseClasses.config;

import DatabaseClasses.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//[https://blog.marcosbarbero.com/multiple-mongodb-connectors-in-spring-boot/]

//[https://www.youtube.com/watch?v=l5KC6OcbuOI]
@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@Configuration
@RequiredArgsConstructor
public class MultipleMongoDBConfig {

    private static final Log log = LogFactory.getLog(MultipleMongoDBConfig.class);

    private final MultipleMongoProperties mongoProperties;


    @Bean
    CommandLineRunner commandLineRunner(ProjectRepository projectRepository) {
        return strings -> {
            // only if the db is empty, call the data via gitlab api.
            // otherwise the data is already in the db, fetch data from the db
            if (projectRepository.findAll().isEmpty()) {
                //projectRepository.save(object for the DB);
            }



            // save methods for other data from api could be added more
        };
    }

}
