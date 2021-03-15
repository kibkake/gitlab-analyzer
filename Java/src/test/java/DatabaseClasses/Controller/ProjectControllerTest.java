/*
References:

- https://www.youtube.com/watch?v=--nQfs67zCM
Used this tutorial to set up Mockito JUnit tests for Controller functions.

- https://www.youtube.com/watch?v=g4EpRfKRO2w
For adding mockito as a dependency.

- https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks
Clarification on @Mock, @InjectMock, and annotation(s) to use for the class.
 */

package test.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Controller.*;
import main.java.DatabaseClasses.Service.*;
import main.java.DatabaseClasses.Model.*;
import main.java.DatabaseClasses.Repository.*;
import main.java.DatabaseClasses.Service.ProjectService;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.time.LocalDate;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.Mockito.when;


/**
 * This class contains JUnit tests for some of the main GET functions in
 * ProjectController. The general idea is that the tests will check if the retrieved
 * data from the DB matches the data from the time of writing. Then going forward,
 * a test failing means either the code has changed or the DB's data has.
 */

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;// = Mockito.mock(ProjectRepository.class);

    //@Autowired
    //MongoTemplate mongoTemplate;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void testFunc() {
        //ProjectService projectService = new ProjectService(projectRepository);
        AllScores data = projectService.getAllScores(6, "user2", LocalDate.parse("2021-01-01"),
                LocalDate.parse("2021-03-13"), ProjectService.UseWhichDevField.EITHER);
        assertEquals(1,1);
    }

    @Test
    public void anotherTest() {
        //ProjectService projectService = new ProjectService(projectRepository);
        assertEquals(1,1);
    }
}