package main.java.DatabaseClasses.Repository.MergeRequest;

import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/** Queries merge Request db to aggregate and get user scores
 *
 */
public class MergeRequestRepositoryCustomImpl implements MergeRequestRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MergeRequestRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<MergeRequestDateScore> getDevsMrsScoreADay(int projectId, String devUserName, LocalDate startDate, LocalDate endDate) {
        //https://stackoverflow.com/questions/62340986/aggregation-with-multiple-criteria
        final Criteria nameMatchCriteria = Criteria.where("contributors.username").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("mergedDate").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                // how to group by date in mongo, our time includes minutes and this complicates a group by
                //https://stackoverflow.com/questions/34577877/mongotemplate-aggregate-group-by-date
                Aggregation.project("mergedDate", "mrScore","mergeRequestIdForASpecificProject")
                        .and(DateOperators.DateToString.dateOf("mergedDate").toString("%Y-%m-%d")).as("groupByDate"),
                Aggregation.group("groupByDate").count().as("numMergeRequests")
                        .push("mergeRequestIdForASpecificProject").as("mergeRequestId")
                        .sum("mrScore").as("mergeRequestScore")
                        .addToSet("mergedDate").as("mergedDate")
        );

        AggregationResults<MergeRequestDateScore> groupResults = mongoTemplate.aggregate(aggregation, MergeRequest.class, MergeRequestDateScore.class);
        List<MergeRequestDateScore> result = groupResults.getMappedResults();
        return result;
    }

    @Override
    public List<MergeRequest> getDevMergeRequests(int projectId, String devUserName, LocalDate startDate, LocalDate endDate) {
        final Criteria nameMatchCriteria = Criteria.where("contributors.username").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("mergedDate").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Query query = new Query();
        query.addCriteria(criterias);
        return mongoTemplate.find(query, MergeRequest.class);
    }

    @Override
    public Double getUserTotalMergeRequestScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate) {
        final Criteria nameMatchCriteria = Criteria.where("contributors.username").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("mergedDate").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("contributors"),
                Aggregation.project("mrScore", "projectId", "mergedDate").and("contributors.username").as("username"),
                Aggregation.match(criterias),
                Aggregation.group("username").sum("mrScore").as("mergeRequestScore")
        );

        AggregationResults<Double> groupResults = mongoTemplate.aggregate(aggregation, MergeRequest.class, Double.class);
        Optional<Double> result = Optional.ofNullable(groupResults.getUniqueMappedResult());
        return result.orElse(0.0);
    }

    @Override
    public MergeRequest getMrByCommitHash(int projectId, String hash){

        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria hashMatchCriteria = Criteria.where("commits.Id").is(hash);

        Criteria criterias = new Criteria().andOperator(projectMatchCriteria, hashMatchCriteria );
        Query query = new Query(criterias);

        final MergeRequest mergeRequests = mongoTemplate.findOne(query, MergeRequest.class);
        return mergeRequests;
    }

}
