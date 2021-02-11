package main.java;

import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Developer.DeveloperController;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.ConnectToGitlab.MergeRequests.MergeRequestConnection;
import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.model.Projects;
import main.java.DatabaseClasses.Repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.List;

/**
 * The main application that invokes SpringBoot's bootRun
 */
@SpringBootApplication
public class Main {

    @Autowired
    ProjectRepository projectRepository;

    /**
     * This is the main method for running Spring Boot
     *
     * @param args arguments to the main method
     */
    public static void main(String[] args){
        User user = User.getInstance();
        user.setServerUrl("https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/");
        user.setToken("cFzzy7QFRvHzfHGpgrr1");

        try {
            ConnectToGitlab.connectGitlab("cFzzy7QFRvHzfHGpgrr1");
        }catch(IOException | ParseException exception){
            System.out.println(exception.getMessage());
        }
        SpringApplication.run(Main.class,args);

        List<MergeRequest> mrs = MergeRequestConnection.getProjectMergeRequests(6, "2021-01-01",
                "2021-02-01");
//        System.out.println(mrs);

        List<Developer> devs = DeveloperController.getDevelopers();
        Developer testDevs = devs.get(4);
        List<MergeRequest> mergeRequests = testDevs.getDevMergeRequests(mrs);
        System.out.println(mergeRequests);

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


            // very simple testing for DB cloud connection
            // after running this, you can see the projects collection under gitlab db was created with this component
            // after saving the object, should comment out the save function so there won't be duplicate
            // you can use delete & save in turn

            Projects projects = new Projects(4, "Testing1");
//            projectRepository.save(projects);
//            projectRepository.delete(projects);
            System.out.println(projectRepository.findAll());
        };
    }
}
