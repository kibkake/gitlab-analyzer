package main.java;

import main.java.ConnectToGitlab.ConnectToGitlab;
import main.java.DatabaseClasses.Model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Arrays;

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
