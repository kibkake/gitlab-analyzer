package main.java;

import DatabaseClasses.repository.ProjectRepository;
import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Commit.CommitController;
import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Developer.DeveloperController;
import main.java.ConnectToGitlab.Project.Project;
import main.java.ConnectToGitlab.Project.ProjectController;
import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.DatabaseFunctions;

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
    private ProjectRepository projectRepository;

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

        // test for database functions
        System.out.println("\n\n"+DatabaseFunctions.retrieveUserToken("test")+"\n\n");
    }

    @Override
    public void run(String... args) throws Exception {
        projectRepository.save(GitLabClient.getProjects());

        // This is for checking in the console if the data from api was saved properly in MongoDBConfig class
        System.out.println(projectRepository.findAll());
    }
}
