package main.java;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Commit.CommitController;
import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Developer.DeveloperController;
import main.java.ConnectToGitlab.Project.Project;
import main.java.ConnectToGitlab.Project.ProjectController;
import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.DatabaseFunctions;
import main.java.Security.Authenticator;

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

        // tests for implemented classes  -- comment out each line for skipping tests
        testDbFunctions();
        testAuthenticator();
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

    /**
     * test method for convenience of testing DatabaseFunctions class
     */
    public static void testDbFunctions(){
        // test add and read tokens
        DatabaseFunctions.addUserToken("test","gklP3oD95mxDs2lFk6Hy4");
        System.out.println("\n\n"+DatabaseFunctions.retrieveUserToken("test")+"\n\n");

        // test user password with database stored password
        System.out.println("Testing User Passwords with Database stored Password:\nuser: Enterprise\npass: thegreyghost\n");
        System.out.println("Test 1 - \"thegrayghost\" ... Expected: false ---> Actual: "+DatabaseFunctions.isUserAuthenticated("Enterprise","thegrayghost"));
        System.out.println("Test 2 - \"thegreyghost\" ... Expected: true ---> Actual: "+DatabaseFunctions.isUserAuthenticated("Enterprise","thegreyghost"));
    }

    /**
     * test method for convenience of testing Authenticator class
     */
    public static void testAuthenticator(){
        String pKey = Authenticator.encrypt("Montana");
        System.out.println("\n\nTesting of Password encryption:\npassword = \"Montana\"\nkey = "+pKey+"\n");
        System.out.println("Test 1 - \"montana\" ... Expected: false ---> Actual: "+pKey.equals(Authenticator.encrypt("montana")));
        System.out.println("Test 2 - \"_Montana\" ... Expected: false ---> Actual: "+pKey.equals(Authenticator.encrypt("_Montana")));
        System.out.println("Test 3 - \"Idaho\" ... Expected: false ---> Actual: "+pKey.equals(Authenticator.encrypt("Idaho")));
        System.out.println("Test 4 - \"Montana\" ... Expected: true ---> Actual: "+pKey.equals(Authenticator.encrypt("Montana")));
    }
}
