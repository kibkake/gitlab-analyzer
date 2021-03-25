package test.java.DatabaseClasses.Controller;

import main.java.Collections.Commit;
import main.java.DatabaseClasses.Repository.Commit.CommitRepository;
import main.java.Main;
import main.java.DatabaseClasses.Service.CommitService;
import main.java.DatabaseClasses.Scores.CommitDateScore;

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
 * This class will contain JUnit tests for some of the main functions in
 * CommitController and CommitService.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Main.class)
public class CommitFunctionsTest {

    @Autowired
    private CommitRepository commitRepository;

    private double precisionLevel = 0.000001;

    @Test
    public void testGetUserCommitsArray() {
        LocalDate start = LocalDate.parse("2021-01-01");
        LocalDate end = LocalDate.parse("2021-03-24");
        CommitService commitService = new CommitService(commitRepository);
        List<CommitDateScore> userCommits = commitService.getUserCommitsArray(6,
                "user2", start, end);
        for (CommitDateScore currentScore: userCommits) {
            LocalDate date = currentScore.getDate();
            if (date.getDayOfMonth() == 24 && date.getMonthValue() == 1) {
                assertEquals(0.6, currentScore.getCommitScore(), precisionLevel);
                assertEquals(2, currentScore.getNumCommits());
            }
            else {
                assertEquals(0.0, currentScore.getCommitScore(), precisionLevel);
                assertEquals(0, currentScore.getNumCommits());
            }
        }
    }
}