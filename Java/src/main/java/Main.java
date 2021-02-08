package main.java;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.ProjectsRepository;
import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.DatabaseFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.text.ParseException;

/**
 * The main application that invokes SpringBoot's bootRun
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

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
//        System.out.println("\n\n"+DatabaseFunctions.retrieveUserToken("test")+"\n\n");

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
