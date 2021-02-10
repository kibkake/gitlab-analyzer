package main.java.DatabaseClasses.Service;

import main.java.DatabaseClasses.Repository.CommitRepository;
import main.java.DatabaseClasses.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * Communicates between rest endpoint and the database
 */
@Service
public class CommitService {
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public List<Commit> getCommitsBetween(String sinceYYYY_MM_DD , String untilYYYY_MM_DD, Integer projectId) {
        OffsetDateTime offSetSince = OffsetDateTime.parse(sinceYYYY_MM_DD);
        OffsetDateTime offSetUntil = OffsetDateTime.parse(untilYYYY_MM_DD);
        Date since = Date.from(offSetSince.toInstant());
        Date until = Date.from(offSetUntil.toInstant());
        return commitRepository.findByDateBetweenAndProjectId(since, until, projectId);
    }

    public void addCommits(List<Commit> commits) {
        commitRepository.saveAll(commits);
    }
}
