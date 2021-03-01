package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class MergeRequestRepositoryCustomImpl implements MergeRequestRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MergeRequestRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<DateScore> devsMrsADay(int projectId, String devUserName) {
        //https://stackoverflow.com/questions/62340986/aggregation-with-multiple-criteria
        final Criteria nameMatchCriteria = Criteria.where("contributors.username").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("project_id").is(projectId);

        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria);
        System.out.println(projectId);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                Aggregation.project( "mergedDate", "mrScore"),
                Aggregation.group("mergedDate").count().as("numMergeRequests")
                        .sum("mrScore").as("mergeRequestScore")
//                        .addToSet("contributors.userName").as("authorName")
                        .addToSet("mergedDate").as("date")
        );

        AggregationResults<DateScore> groupResults = mongoTemplate.aggregate(aggregation, MergeRequest.class, DateScore.class);
        List<DateScore> result = groupResults.getMappedResults();
        return result;
    }

}
