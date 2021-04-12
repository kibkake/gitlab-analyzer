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
import main.java.Collections.DateScoreDiff;
import main.java.Collections.Note;
import main.java.Collections.Developer;
import main.java.Collections.MergeRequest;
import main.java.Collections.Commit;
import main.java.Collections.Diff;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void testMRsAndCommitScoresPerDay() {
        ProjectService projectService = createProjectServiceObject();
        ProjectController projectController = new ProjectController(projectService);
        List<DateScore> scores = projectController.getDevMRsAndCommitScorePerDay(
                6, "user2", "2021-01-01", "2021-04-10", "either");
        assertEquals(5, scores.size());

        String expectedDateAsString = null;
        double expectedCommitScore;
        double expectedMRScore;
        int expectedNumCommits;
        int expectedNumMRs;
        List<DateScoreDiff> expectedCommitDiffs = null;
        List<DateScoreDiff> expectedMRDiffs = null;

        for (int i = 0; i < scores.size(); i++) {
            if (i == 0) {
                expectedDateAsString = "2021-01-24";
                expectedCommitScore = 0.6;
                expectedMRScore = 0.6;
                expectedNumCommits = 2;
                expectedNumMRs = 1;
                expectedCommitDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 0.0), new DateScoreDiff("src/Main.java", 0.2),
                                new DateScoreDiff("src/NewClass.java", 0.4)));
                expectedMRDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 0.2), new DateScoreDiff("src/NewClass.java", 0.4)));
            }
            else if (i == 1) {
                expectedDateAsString = "2021-02-20";
                expectedCommitScore = 120.8;
                expectedMRScore = 60.4;
                expectedNumCommits = 6;
                expectedNumMRs = 3;
                expectedCommitDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 16.4), new DateScoreDiff("src/NewClass.java", 14.8),
                                new DateScoreDiff("src/Main.java", 16.4), new DateScoreDiff("src/NewClass.java", 14.8),
                                new DateScoreDiff("src/Main.java", 11.8), new DateScoreDiff("src/NewClass.java", 9.2),
                                new DateScoreDiff("src/Main.java", 11.8), new DateScoreDiff("src/NewClass.java", 9.2),
                                new DateScoreDiff("src/Main.java", 8.2), new DateScoreDiff("src/Main.java", 8.2)));
                expectedMRDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 16.4), new DateScoreDiff("src/NewClass.java", 14.8),
                                new DateScoreDiff("src/Main.java", 11.8), new DateScoreDiff("src/NewClass.java", 9.2),
                                new DateScoreDiff("src/Main.java", 8.2)));
            }
            else if (i == 2) {
                expectedDateAsString = "2021-02-18";
                expectedCommitScore = 32.8;
                expectedMRScore = 16.4;
                expectedNumCommits = 4;
                expectedNumMRs = 2;
                expectedCommitDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/NewClass.java", 9.4), new DateScoreDiff("src/NewClass.java", 9.4),
                                new DateScoreDiff("src/Main.java", 7.0), new DateScoreDiff("src/Main.java", 7.0)));
                expectedMRDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/NewClass.java", 9.4), new DateScoreDiff("src/Main.java", 7.0)));
            }
            else if (i == 3) {
                expectedDateAsString = "2021-02-19";
                expectedCommitScore = 39.6;
                expectedMRScore = 19.8;
                expectedNumCommits = 2;
                expectedNumMRs = 1;
                expectedCommitDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 19.8), new DateScoreDiff("src/Main.java", 19.8)));
                expectedMRDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 19.8)));
            }
            else {
                expectedDateAsString = "2021-02-06";
                expectedCommitScore = 1.0;
                expectedMRScore = 0.0;
                expectedNumCommits = 1;
                expectedNumMRs = 0;
                expectedCommitDiffs = new ArrayList<>(
                        Arrays.asList(new DateScoreDiff("src/Main.java", 1.0)));
                expectedMRDiffs = new ArrayList<>();
            }

            DateScore dateScore = scores.get(i);
            assertEquals(expectedDateAsString, dateScore.getDate().format(dateFormatter));
            assertEquals(expectedCommitScore, dateScore.getCommitScore(), epsilon);
            assertEquals(expectedMRScore, dateScore.getMergeRequestScore(), epsilon);
            assertEquals(expectedNumCommits, dateScore.getNumCommits());
            assertEquals(expectedNumMRs, dateScore.getNumMergeRequests());
            assertEquals(expectedCommitDiffs, dateScore.getCommitDiffs());
            assertEquals(expectedMRDiffs, dateScore.getMergeRequestDiffs());
            assertEquals("user2", dateScore.getUserName());
        }
    }

    @Test
    public void testGetDevMergeRequests() {
        ProjectService projectService = createProjectServiceObject();
        ProjectController projectController = new ProjectController(projectService);
        List<MergeRequest> mergeRequests = projectController.getDevMergeRequests(6, "user2", "2021-01-01", "2021-04-11");
        assertEquals(7, mergeRequests.size());

        int expectedID;
        String expectedTitle = null;
        int expectedNumContributors;
        List<String> expectedCommitTitles = null;
        String expectedSHA = null;
        double expectedMRScore;
        List<String> expectedNoteBodies = null;
        List<Double> expectedDiffScores = null;
        double expectedSumOfCommits;
        int expectedIID;

        for (int i = 0; i < mergeRequests.size(); i++) {
            if (i == 0) {
                expectedID = 59;
                expectedTitle = "Update src/NewClass.java, src/Main.java files";
                expectedNumContributors = 1;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("Update src/NewClass.java, src/Main.java files"));
                expectedSHA = "0f0822a97bbb9500e2e682e60a590b9bd4504956";
                expectedMRScore = 31.2;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("mentioned in commit 45b56d337b267631f8c0a8397ff6a769b8e2bfe7"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(16.4, 14.8));
                expectedSumOfCommits = 31.2;
                expectedIID = 12;
            }
            else if (i == 1) {
                expectedID = 58;
                expectedTitle = "Update src/NewClass.java, src/Main.java files";
                expectedNumContributors = 1;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("Update src/NewClass.java, src/Main.java files"));
                expectedSHA = "3206cc8d46bd4c28ca24d6c9b29309e87ba50d09";
                expectedMRScore = 21.0;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("mentioned in commit 3ba4fa806c4ffa225e593646ed6fc5b4ae9694cb"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(11.8, 9.2));
                expectedSumOfCommits = 21.0;
                expectedIID = 11;
            }
            else if (i == 2) {
                expectedID = 57;
                expectedTitle = "Update src/Main.java";
                expectedNumContributors = 2;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("Update src/Main.java"));
                expectedSHA = "a6395a545cf9ef9751258c73803076a19628b36e";
                expectedMRScore = 8.2;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("Bump", "mentioned in commit 0d7966c533d294b94ed371cd6813743441f61c34"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(8.2));
                expectedSumOfCommits = 8.2;
                expectedIID = 10;
            }
            else if (i == 3) {
                expectedID = 54;
                expectedTitle = "Update src/Main.java";
                expectedNumContributors = 2;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("Update src/Main.java"));
                expectedSHA = "50ceff672341fa23e126a654e0629e71506af05b";
                expectedMRScore = 19.8;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("Bump", "Bump", "Bump", "Bump", "mentioned in commit 700740418c15249fce74e4bf4e62c5357f0393ae"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(19.8));
                expectedSumOfCommits = 19.8;
                expectedIID = 7;
            }
            else if (i == 4) {
                expectedID = 52;
                expectedTitle = "Update src/NewClass.java";
                expectedNumContributors = 1;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("Update src/NewClass.java"));
                expectedSHA = "84eb119d48db386938df72b8c2452cf882e8c802";
                expectedMRScore = 9.4;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("mentioned in commit 37bcc2ad306470d6042fdb03a602844e91608474"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(9.4));
                expectedSumOfCommits = 9.4;
                expectedIID = 5;
            }
            else if (i == 5) {
                expectedID = 51;
                expectedTitle = "Update src/Main.java";
                expectedNumContributors = 1;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("Update src/Main.java"));
                expectedSHA = "073bcbb678ce6b652f9e2c72ccabead01c17e165";
                expectedMRScore = 7.0;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("mentioned in commit 711f899062d24c6ede0d0f15e952fa53758b57bf"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(7.0));
                expectedSumOfCommits = 7.0;
                expectedIID = 4;
            }
            else {
                expectedID = 6;
                expectedTitle = "Feature2";
                expectedNumContributors = 1;
                expectedCommitTitles = new ArrayList<>(
                        Arrays.asList("f1-3a commit", "f2-2u", "f2-1u"));
                expectedSHA = "39979003d3aeb6aa8fd832d7a89e34d5272e4e3a";
                expectedMRScore = 0.6;
                expectedNoteBodies = new ArrayList<>(
                        Arrays.asList("mentioned in commit af214df90061d1ede4f98bc246d834d80cc3a99d"));
                expectedDiffScores = new ArrayList<>(Arrays.asList(0.2, 0.4));
                expectedSumOfCommits = 0.6;
                expectedIID = 2;
            }

            MergeRequest mergeRequest = mergeRequests.get(i);
            assertEquals(expectedID, mergeRequest.getId());
            assertEquals(expectedTitle, mergeRequest.getTitle());
            assertEquals(expectedNumContributors, mergeRequest.getContributors().size());
            List<Commit> commits = mergeRequest.getCommits();
            List<String> commitTitles = new ArrayList<>();
            for (Commit commit : commits) {
                commitTitles.add(commit.getTitle());
            }
            assertEquals(expectedCommitTitles, commitTitles);
            assertEquals(expectedSHA, mergeRequest.getSha());
            assertEquals(expectedMRScore, mergeRequest.getMrScore(), epsilon);
            List<Note> notes = mergeRequest.getAllNotes();
            List<String> noteBodies = new ArrayList<>();
            for (Note note : notes) {
                noteBodies.add(note.getBody());
            }
            assertEquals(expectedNoteBodies, noteBodies);
            List<Diff> diffs = mergeRequest.getDiffs();
            List<Double> diffScores = new ArrayList<>();
            for (Diff diff : diffs) {
                diffScores.add(diff.getDiffScore());
            }
            assertEquals(expectedDiffScores, diffScores);
            assertEquals(expectedSumOfCommits, mergeRequest.getSumOfCommits(), epsilon);
            assertEquals(expectedIID, mergeRequest.getMergeRequestIdForASpecificProject());
        }
    }

    // Helper functions:

    private void printList(List<DateScoreDiff> list) {
        System.out.println("{");
        for (DateScoreDiff current: list) {
            System.out.println(current.getNew_path() + "," + current.getDiffScore());
        }
        System.out.println("}\n\n");
    }

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