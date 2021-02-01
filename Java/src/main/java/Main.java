package main.java;

import Commit.Commit;
import Commit.CommitController;
import Project.ProjectController;
import User.User;
import User.UserController;
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
//        }catch(IOException excpetion){
//            System.out.println(excpetion.getMessage());
//        }
        SpringApplication.run(Main.class,args);
//        UserController.getUsers();
        CommitController.getProjectCommits(8, "2021-01-01", "2021-01-26");
//        List<Commit> johnsCommits = Commit.getCommitByUser(allCommits, "John Doknjas");
    }

    /**https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/8/repository/commits?since=2021-01-01T00:00:00-08:00&access_token=cFzzy7QFRvHzfHGpgrr1
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
