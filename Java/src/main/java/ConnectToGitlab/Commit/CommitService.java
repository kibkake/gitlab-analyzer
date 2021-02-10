package main.java.ConnectToGitlab.Commit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<Commit> getCommits(String sinceYYYY_MM_DD, String untilYYYY_MM_DD) throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        Date since = df1.parse(sinceYYYY_MM_DD);
        Date until = df1.parse(sinceYYYY_MM_DD);

        return commitRepository.findByDateBetween(since, until);
    }

}
