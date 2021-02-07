package DatabaseClasses.config;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//[https://blog.marcosbarbero.com/multiple-mongodb-connectors-in-spring-boot/]
//[https://www.youtube.com/watch?v=l5KC6OcbuOI]
@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProjectRepository projectRepository) {
        return strings -> {

            projectRepository.save(new Projects(1, "Laine", "0206"));
            System.out.println("hello");

            System.out.println(projectRepository.findAll());

            // save methods for other data from api could be added more
        };
    }

}
