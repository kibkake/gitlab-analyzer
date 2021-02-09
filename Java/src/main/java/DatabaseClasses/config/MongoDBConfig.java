package DatabaseClasses.config;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

//[https://blog.marcosbarbero.com/multiple-mongodb-connectors-in-spring-boot/]
//[https://www.youtube.com/watch?v=l5KC6OcbuOI]
@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@Configuration
@Component
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProjectRepository projectRepository) {
        return strings -> {

            Projects projectExample = new Projects(1, "test");

            projectRepository.save(projectExample);

            System.out.println("hello");
            System.out.println(projectRepository.findAll());

            // save methods for other data from api could be added more
        };
    }

}
