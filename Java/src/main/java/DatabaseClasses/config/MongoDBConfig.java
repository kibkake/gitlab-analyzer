package DatabaseClasses.config;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.MemberRepository;
import DatabaseClasses.repository.ProjectsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//[https://blog.marcosbarbero.com/multiple-mongodb-connectors-in-spring-boot/]
//[https://www.youtube.com/watch?v=l5KC6OcbuOI]
@EnableMongoRepositories(basePackageClasses = ProjectsRepository.class)

@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProjectsRepository projectsRepository) {
        return strings -> {

            projectsRepository.save(new Projects(1, "Laine", "0206"));
            System.out.println("hello");

            System.out.println(projectsRepository.findAll());

            // save methods for other data from api could be added more
        };
    }

}
