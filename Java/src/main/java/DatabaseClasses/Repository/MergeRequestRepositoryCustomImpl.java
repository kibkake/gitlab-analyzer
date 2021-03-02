package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.util.List;

public class MergeRequestRepositoryCustomImpl implements MergeRequestRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MergeRequestRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<CommitDateScore> devsMrsADay(int projectId, String devUserName, LocalDate startDate, LocalDate endDate) {
        //https://stackoverflow.com/questions/62340986/aggregation-with-multiple-criteria
        final Criteria nameMatchCriteria = Criteria.where("contributors.username").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("date").gte(startDate).lte(endDate);


        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                Aggregation.project( "mergedDate", "mrScore"),
                Aggregation.group("mergedDate").count().as("numMergeRequests")
                        .sum("mrScore").as("mergeRequestScore")
                        .addToSet("contributors.userName").as("authorName")
                        .addToSet("mergedDate").as("date")
        );

        AggregationResults<CommitDateScore> groupResults = mongoTemplate.aggregate(aggregation, MergeRequest.class, CommitDateScore.class);
        List<CommitDateScore> result = groupResults.getMappedResults();
        return result;
    }

}
