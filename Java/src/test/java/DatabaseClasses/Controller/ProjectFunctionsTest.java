/*
References:

- https://www.youtube.com/watch?v=--nQfs67zCM
Tutorial on setting up Mockito JUnit tests.

- https://www.youtube.com/watch?v=g4EpRfKRO2w
For adding mockito as a dependency.

- https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks
Clarification on @Mock, @InjectMock, and annotation(s) to use for the class.

- https://www.youtube.com/watch?v=3UpK2m94nHk
Useful tutorial on mock objects.

- https://medium.com/javarevisited/writing-a-unit-test-using-spring-boot-part-2-b16847484cb9
Tutorial on setting up unit testing.

The above references were useful mostly for learning about Mockito and Mocks,
but currently Mocks are not going to be used for the tests. I found out that
for testing the actual database, it probably isn't the correct method to use.

The links below are the resources which were mainly used for the current code:

- https://stackoverflow.com/questions/28177370/how-to-format-localdate-to-string
For seeing how to convert a LocalDate to a String.

 - https://stackoverflow.com/questions/23435937/how-to-test-spring-data-repositories
Followed heez's answer for which annotations to use.

- https://stackoverflow.com/questions/49635396/runwithspringrunner-class-vs-runwithmockitojunitrunner-class/49647467
Info on using @RunWith(SpringRunner.class) compared to @RunWith(MockitoJUnitRunner.class).

- https://stackoverflow.com/questions/39074213/springrunner-dependency-class-couldnt-be-located
The top answer showed what gradle dependency to include for SpringRunner.

- https://stackoverflow.com/questions/39084491/unable-to-find-a-springbootconfiguration-when-doing-a-jpatest
What to include in parenthesis for @SpringBootTest, to avoid an IllegalStateException.

*/
package test.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Controller.ProjectController;
import main.java.DatabaseClasses.Service.*;
import main.java.DatabaseClasses.Scores.*;
import main.java.Collections.Project;
import main.java.DatabaseClasses.Repository.Project.ProjectRepository;
import main.java.DatabaseClasses.Repository.MergeRequest.MergeRequestRepository;
import main.java.DatabaseClasses.Repository.Developer.DeveloperRepository;
import main.java.DatabaseClasses.Repository.Commit.CommitRepository;
import main.java.DatabaseClasses.Repository.Snapshot.SnapshotRepository;
import main.java.Main;
import main.java.DatabaseClasses.Service.ProjectService;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains JUnit tests for some of the main GET functions in
 * ProjectController. The general idea is that the tests will check if the retrieved
 * data from the DB matches the data from the time of writing. Then going forward,
 * a test failing means either the code has changed or the DB's data has.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Main.class)
public class ProjectFunctionsTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MergeRequestRepository mergeRequestRepository;

    @Autowired
    private CommitRepository commitRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private SnapshotRepository snapshotRepository;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    public void testingFindProjectWithRepositoryAndService() {
        Project testProjFromRepo = projectRepository.findProjectById(6);
        assertEquals(6, testProjFromRepo.getId());
        ProjectService projectService = new ProjectService(projectRepository,
                mergeRequestRepository, commitRepository, developerRepository, snapshotRepository);
        Project testProjFromService = projectService.getProject(6);
        assertEquals(6, testProjFromService.getId());
        assertEquals(testProjFromRepo, testProjFromService);
    }

    @Test
    public void testScoresPerDay() {
        ProjectService projectService = new ProjectService(projectRepository,
                mergeRequestRepository, commitRepository, developerRepository, snapshotRepository);
        LocalDate start = LocalDate.parse("2021-01-01");
        LocalDate end = LocalDate.parse("2021-03-19");
        List<DateScore> user2Scores = projectService.getScoresPerDayForMRsAndCommits(
                6, "user2", start, end, ProjectService.UseWhichDevField.USERNAME);
        assertEquals("2021-01-24", user2Scores.get(0).getDate().format(dateFormatter));
    }

    @Test
    public void testNumMRs() {
        ProjectService projectService = new ProjectService(projectRepository,
                mergeRequestRepository, commitRepository, developerRepository, snapshotRepository);
        LocalDate start = LocalDate.parse("2021-01-01");
        LocalDate end = LocalDate.parse("2021-02-28");
        int numMRs = projectService.getNumDevMergeRequests(6, "user2",
                start, end);
        assertEquals(6, numMRs);
    }
}