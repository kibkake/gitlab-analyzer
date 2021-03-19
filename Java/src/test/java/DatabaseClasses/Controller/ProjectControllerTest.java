/*
References:

- https://www.youtube.com/watch?v=--nQfs67zCM
Used this tutorial to set up Mockito JUnit tests for Controller functions.

- https://www.youtube.com/watch?v=g4EpRfKRO2w
For adding mockito as a dependency.

- https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks
Clarification on @Mock, @InjectMock, and annotation(s) to use for the class.

- https://www.youtube.com/watch?v=3UpK2m94nHk
Useful tutorial on mock objects.

 */
package test.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Controller.ProjectController;
import main.java.DatabaseClasses.Service.*;
import main.java.DatabaseClasses.Model.*;
import main.java.Model.Project;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import main.java.DatabaseClasses.Service.ProjectService;
import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.time.LocalDate;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


/**
 * This class contains JUnit tests for some of the main GET functions in
 * ProjectController. The general idea is that the tests will check if the retrieved
 * data from the DB matches the data from the time of writing. Then going forward,
 * a test failing means either the code has changed or the DB's data has.
 */

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;// = Mockito.mock(ProjectRepository.class);

    //@Autowired
    //MongoTemplate mongoTemplate;

    @InjectMocks
    private ProjectService projectService;

    //private Project project;

    @Before
    public void setUp()/* throws Exception */{
        MockitoAnnotations.initMocks(this);
        //project = new Project();
    }

    //@Test
    //@Before
    /*public void testFunc() {
        //ProjectService projectService = new ProjectService(projectRepository);
        LocalDate start = LocalDate.parse("2021-01-01");
        LocalDate end = LocalDate.parse("2021-02-28");
        //int numMRs = projectService.getNumDevMergeRequests(6, "user2",
        //        start, end);
        List<Project> lst = projectController.getAllProjects();
    }*/

    @Test
    public void anotherTest() {
        ProjectService projectService = new ProjectService(projectRepository);
        assertEquals(1,1);

        //when(projectService.getProject(anyInt())).thenReturn(project);

    }
}