package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Fields.fields;

public class MergeRequestRepositoryCustomImpl implements MergeRequestRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MergeRequestRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<MergeRequestDateScore> devsMrsScoreADay(int projectId, String devUserName, LocalDate startDate, LocalDate endDate) {
        //https://stackoverflow.com/questions/62340986/aggregation-with-multiple-criteria
        final Criteria nameMatchCriteria = Criteria.where("contributors.username").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("mergedDate").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                // how to group by date in mongo, our time includes minutes and this complicates a group by
                //https://stackoverflow.com/questions/34577877/mongotemplate-aggregate-group-by-date
                Aggregation.project( "mergedDate", "mrScore")
                        .andExpression("year(mergedDate)").as("year")
                        .andExpression("month(mergedDate)").as("month")
                        .andExpression("dayOfMonth(mergedDate)").as("day"),
                Aggregation.group(fields().and("year").and("month").and("day")).count().as("numMergeRequests")
                        .sum("mrScore").as("mergeRequestScore")
//                        .addToSet("contributors.username").as("authorName")
                        .addToSet("mergedDate").as("date")
        );

        AggregationResults<MergeRequestDateScore> groupResults = mongoTemplate.aggregate(aggregation, MergeRequest.class, MergeRequestDateScore.class);
        List<MergeRequestDateScore> result = groupResults.getMappedResults();
        return result;
    }

    @Override
    public Object userTotalMergeRequestScore(int projectId, String devUserName, LocalDate startDate, LocalDate endDate) {
        final Criteria nameMatchCriteria = Criteria.where("contributors.name").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);
        final Criteria dateMatchCriteria = Criteria.where("mergedDate").gte(startDate).lte(endDate);
        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria, dateMatchCriteria);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                Aggregation.project( "mrScore", "authorName"),
                Aggregation.group("authorName").sum("mrScore").as("mergeRequestTotalScore")
        );

        AggregationResults<Object> groupResults = mongoTemplate.aggregate(aggregation, MergeRequest.class, Object.class);
        List<Object> result = groupResults.getMappedResults();
        return result;

    }

}
