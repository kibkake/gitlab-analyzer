/*
References:

- https://www.youtube.com/watch?v=--nQfs67zCM
Used this tutorial to set up Mockito JUnit tests for Controller functions.

- https://www.youtube.com/watch?v=g4EpRfKRO2w
For adding mockito as a dependency.
 */

package test.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Controller.*;
import main.java.DatabaseClasses.Service.*;
import main.java.DatabaseClasses.Model.*;
import main.java.DatabaseClasses.Repository.*;
import org.junit.Test;

import static org.junit.Assert.*;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.time.LocalDate;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;


/**
 * This class contains JUnit tests for some of the main GET functions in
 * ProjectController. The general idea is that the tests will check if the retrieved
 * data from the DB matches the data from the time of writing. Then going forward,
 * a test failing means either the code has changed or the DB's data has.
 */

//@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

    @Test
    void testFunc() {
        ProjectService projectSerivce = new ProjectService(null);
        AllScores data = projectSerivce.getAllScores(6, "user2", LocalDate.parse("2021-01-01"),
                LocalDate.parse("2021-03-13"), ProjectService.UseWhichDevField.EITHER);

    }

    @Test
    void anotherTest() {
        ProjectService projectService = new ProjectService(projectRepository);
    }
}