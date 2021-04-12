package main.java.DatabaseClasses.Repository.Commit;

import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.Functions.LocalDateFunctions;
import main.java.Collections.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;
import java.util.*;

/*** Implements custom quires to get users commits and scores through aggregation and other techniques with
 * mongoTemplate
 */

public class CommitRepositoryCustomImpl implements CommitRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CommitRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

//  https://gist.github.com/normoes/53c46a3ef2bbe3a1bff817573362f6ee
//  Follows desing patter needed for custom implementation in sprin
    @Override
    public List<CommitDateScore> getDevCommitDateScore(int projectId, String devUserName,
                                                       LocalDateTime startDate, LocalDateTime endDate) {

        //https://stackoverflow.com/questions/62340986/aggregation-with-multiple-criteria
        final Criteria nameMatchCriteria = Criteria.where("authorName").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("date").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                Aggregation.project("authorName", "date", "commitScore", "commitId")
                .and(DateOperators.DateToString.dateOf("date").toString("%Y-%m-%d")).as("groupByDate"),
                Aggregation.group("groupByDate")
                        .sum("commitScore").as("commitScore")
                        .addToSet("groupByDate").as("date")
                        .count().as("numCommits")
                        .push("commitId").as("commitIds")


        );

        AggregationResults<CommitDateScore> groupResults = mongoTemplate.aggregate(aggregation, Commit.class,
                CommitDateScore.class);
        List<CommitDateScore> result = groupResults.getMappedResults();
        return result;
    }

    @Override
    public Double userTotalCommitScore(int projectId, String devUserName, LocalDateTime startDate, LocalDateTime endDate) {
        final Criteria nameMatchCriteria = Criteria.where("authorName").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("date").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project( "commitScore", "authorName"),
                Aggregation.match(criterias),
                Aggregation.group("authorName").sum("commitScore").as("commitTotalScore")
        );

        AggregationResults<Double> groupResults = mongoTemplate.aggregate(aggregation, Commit.class, Double.class);
        Optional<Double> result = Optional.ofNullable(groupResults.getUniqueMappedResult());
        return result.orElse(0.0);

    }

    @Override
    public List<CommitDateScore> getCommitsWithEveryDateBetweenRange(int projectId, String devUserName, LocalDateTime startDate, LocalDateTime endDate) {
        List<CommitDateScore> userCommitScores = new ArrayList<>(getDevCommitDateScore(projectId, devUserName, startDate, endDate));

        ArrayList<LocalDateTime> dates = LocalDateFunctions.generateRangeOfDates(startDate, endDate);
        for(LocalDateTime date: dates){
            if(!containsDate(userCommitScores, date)) {
                CommitDateScore scoreForDate = new CommitDateScore(date, 0, 0);
                userCommitScores.add(scoreForDate);
            }
        }
        userCommitScores.sort(Comparator.comparing(CommitDateScore::getDate));
        return userCommitScores;
    }

    public boolean containsDate(final List<CommitDateScore> UserScores, final LocalDateTime date){
        return UserScores.stream().anyMatch(scores -> scores.getDate().compareTo(date) == 0);
    }

}
