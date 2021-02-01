package main.java;
import main.java.Commit.CommitController;
import main.java.Commit.Commit;

import main.java.ConnectToGitlab.ConnectToGitlab;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.List;

import static main.java.Commit.Commit.*;

/**
 * The main application that invokes SpringBoot's bootRun
 */
@SpringBootApplication
public class Main {

    /**
     * This is the main method for running Spring Boot
     *
     * @param args arguments to the main method
     */
    public static void main(String[] args){
//        try {
//            ConnectToGitlab.connectGitlab("cFzzy7QFRvHzfHGpgrr1");
//        }catch(IOException exception){
//            System.out.println(exception.getMessage());
//        }
        SpringApplication.run(Main.class,args);
        List<Commit> commits = CommitController.getProjectCommits(8, "2021-01-01", "2021-01-27");
        List<Commit> userCommits = getCommitByUser(commits, "John Doknjas");
        System.out.println(userCommits);
    }

    /**
     * The following method is provided from spring.io and only prints information about beans created by our application
     *
     * @see  <a href="https://spring.io/guides/gs/spring-boot/">Spring IO</a>
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.print("Exporting Beans to Log file...");
            PrintWriter writer = new PrintWriter("log.txt");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                writer.write(beanName);
            }
            writer.flush();
            writer.close();

            System.out.println("done");

            // indicate running status
            System.out.println("Server broadcasting on localhost:8080");
        };
    }
}
