package main.java.ConnectToGitlab.Commit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class CommitService {
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public List<Commit> getCommits( String sinceYYYY_MM_DD , String untilYYYY_MM_DD) {
        OffsetDateTime offSetSince = OffsetDateTime.parse(sinceYYYY_MM_DD);
        OffsetDateTime offSetUntil = OffsetDateTime.parse(untilYYYY_MM_DD);
        Date since = Date.from(offSetSince.toInstant());
        Date until = Date.from(offSetUntil.toInstant());
        System.out.println(commitRepository.findByDateBetween(since, until));
        return commitRepository.findByDateBetween(since, until);
    }

}
