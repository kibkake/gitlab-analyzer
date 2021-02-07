package main.java;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.ProjectRepository;
import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.DatabaseFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.text.ParseException;

/**
 * The main application that invokes SpringBoot's bootRun
 */
//@EnableMongoRepositories(basePackageClasses = ProjectRepository.class)
@SpringBootApplication
public class Main implements CommandLineRunner {

//    @Autowired
//    ProjectRepository projectRepository;
    /**
     * This is the main method for running Spring Boot
     *
     * @param args arguments to the main method
     */
    public static void main (String[] args){
        SpringApplication.run (Main.class, args);
    }

    @Override
    public void run (String... args) throws Exception {
        User user = User.getInstance();
        user.setServerUrl("https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/");
        user.setToken("cFzzy7QFRvHzfHGpgrr1");

        try {
            ConnectToGitlab.connectGitlab("cFzzy7QFRvHzfHGpgrr1");
        } catch(IOException | ParseException exception){
            System.out.println(exception.getMessage());
        }

        // test for database functions
        System.out.println("\n\n"+DatabaseFunctions.retrieveUserToken("test")+"\n\n");

//        projectRepository.save(new Projects(1, "Laine", "0206"));

        // This is for checking in the console if the data from api was saved properly in MongoDBConfig class
        System.out.println("test");
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
            }
        };
    }
}
