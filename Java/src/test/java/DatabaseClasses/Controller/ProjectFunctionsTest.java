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
import main.java.DatabaseClasses.Scores.DateScore;
import main.java.Collections.Note;
import main.java.Collections.Developer;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;

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

    private double epsilon = 0.00001;


    @Test
    public void testingFindProjectWithRepositoryAndService() {
        Project testProjFromRepo = projectRepository.findProjectById(6);
        assertEquals(6, testProjFromRepo.getId());
        ProjectService projectService = createProjectServiceObject();
        Project testProjFromService = projectService.getProject(6);
        assertEquals(6, testProjFromService.getId());
        assertEquals(testProjFromRepo, testProjFromService);
    }

    @Test
    public void testScoresPerDay() {
        ProjectService projectService = createProjectServiceObject();
        LocalDate start = LocalDate.parse("2021-01-01");
        LocalDate end = LocalDate.parse("2021-03-19");
        List<DateScore> user2Scores = projectService.getScoresPerDayForMRsAndCommits(
                6, "user2", start, end, ProjectService.UseWhichDevField.USERNAME);
        assertEquals("2021-01-24", user2Scores.get(0).getDate().format(dateFormatter));
    }

    @Test
    public void testNumMRs() {
        ProjectService projectService = createProjectServiceObject();
        LocalDate start = LocalDate.parse("2021-01-01");
        LocalDate end = LocalDate.parse("2021-02-28");
        int numMRs = projectService.getNumDevMergeRequests(6, "user2",
                start, end);
        assertEquals(7, numMRs);
    }

    @Test
    public void testCommitScoresPerDay() {
        ProjectService projectService = createProjectServiceObject();
        ProjectController projectController = new ProjectController(projectService);
        List<DateScore> commitScoresPerDay = projectController.getDevCommitScoresWithDates(
                6, "user2", "2021-01-01", "2021-04-10", "either");
        assertEquals(5, commitScoresPerDay.size());
        DateScore firstElement = commitScoresPerDay.get(0);
        assertEquals(0.6, firstElement.getCommitScore(), epsilon);
        assertEquals(2, firstElement.getNumCommits());
        assertEquals("2021-01-24", firstElement.getDate().format(dateFormatter));

        DateScore lastElement = commitScoresPerDay.get(commitScoresPerDay.size()-1);
        assertEquals("2021-02-06", lastElement.getDate().format(dateFormatter));
        assertEquals(0, lastElement.getMergeRequestScore(), epsilon);
        assertEquals("user2", lastElement.getUserName());
        assertEquals(1, lastElement.getNumCommits());
        assertEquals(1.0, lastElement.getCommitScore(), epsilon);
        assertEquals(1, lastElement.getCommitDiffs().size());
    }

    @Test
    public void testTopTenDevNotes() throws ParseException {
        ProjectService projectService = createProjectServiceObject();
        ProjectController projectController = new ProjectController(projectService);
        List<Note> topNotes = projectController.getTopTenDevNotes(6, "user2",
                "2021-01-01", "2021-04-10");
        assertEquals(10, topNotes.size());

        int expectedId;
        String expectedBody = null;
        int expectedScore;
        int expectedWordCount;
        boolean expectedIsIssueNote;
        String expectedFormattedDate = null;

        for (int i = 0; i < topNotes.size(); i++) {
            if (i == 0) {
                expectedId = 15;
                expectedBody = "changed due date to April 30, 2021";
                expectedScore = 0;
                expectedWordCount = 7;
                expectedIsIssueNote = true;
                expectedFormattedDate = "2021-01-30";
            }
            else if (i == 1) {
                expectedId = 662;
                expectedBody = "mentioned in commit 711f899062d24c6ede0d0f15e952fa53758b57bf";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = false;
                expectedFormattedDate = "2021-02-18";
            }
            else if (i == 2) {
                expectedId = 663;
                expectedBody = "mentioned in commit 37bcc2ad306470d6042fdb03a602844e91608474";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = false;
                expectedFormattedDate = "2021-02-18";
            }
            else if (i == 3) {
                expectedId = 668;
                expectedBody = "mentioned in commit 700740418c15249fce74e4bf4e62c5357f0393ae";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = false;
                expectedFormattedDate = "2021-02-19";
            }
            else if (i == 4) {
                expectedId = 671;
                expectedBody = "mentioned in commit 0d7966c533d294b94ed371cd6813743441f61c34";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = false;
                expectedFormattedDate = "2021-02-20";
            }
            else if (i == 5) {
                expectedId = 672;
                expectedBody = "mentioned in commit 3ba4fa806c4ffa225e593646ed6fc5b4ae9694cb";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = false;
                expectedFormattedDate = "2021-02-20";
            }
            else if (i == 6) {
                expectedId = 673;
                expectedBody = "mentioned in commit 45b56d337b267631f8c0a8397ff6a769b8e2bfe7";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = false;
                expectedFormattedDate = "2021-02-20";
            }
            else if (i == 7) {
                expectedId = 16;
                expectedBody = "This is note #1";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = true;
                expectedFormattedDate = "2021-02-06";
            }
            else if (i == 8) {
                expectedId = 17;
                expectedBody = "this is note #2";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = true;
                expectedFormattedDate = "2021-02-06";
            }
            else {
                expectedId = 675;
                expectedBody = "adding comments in issue";
                expectedScore = 0;
                expectedWordCount = 4;
                expectedIsIssueNote = true;
                expectedFormattedDate = "2021-02-21";
            }
            Note note = topNotes.get(i);
            assertEquals(expectedId, note.getId());
            assertEquals(expectedBody, note.getBody());
            assertEquals(expectedScore, note.getScore());
            assertEquals(expectedWordCount, note.getWordCount());
            assertEquals(expectedIsIssueNote, note.getIssueNote());
            assertEquals(expectedFormattedDate, note.getFormattedDate());
            Developer authorOfNote = note.getAuthor();
            assertTrue(isUser2Developer(authorOfNote));
        }
    }

    // Helper functions:

    private boolean isUser2Developer(Developer dev) {
        // This function returns true if developer matches what's expected for user2
        // in project 6.
        return (dev.getProjectId() == 0 && dev.getName().equals("user2 user2")
                && dev.getUsername().equals("user2") && dev.getDevId() == 11);
    }

    private ProjectService createProjectServiceObject() {
        ProjectService projectService = new ProjectService(projectRepository,
                mergeRequestRepository, commitRepository, developerRepository, snapshotRepository);
        return projectService;
    }
}