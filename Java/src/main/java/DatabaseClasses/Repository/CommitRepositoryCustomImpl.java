package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.Model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class CommitRepositoryCustomImpl implements CommitRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CommitRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

//        https://gist.github.com/normoes/53c46a3ef2bbe3a1bff817573362f6ee
//     Follows desing patter needed for custom implementation in spring
    @Override
    public List<CommitDateScore> getDevDateScore(int projectId, String devUserName) {

        //https://stackoverflow.com/questions/62340986/aggregation-with-multiple-criteria
        final Criteria nameMatchCriteria = Criteria.where("authorName").is(devUserName);
        final Criteria projectMatchCriteria = Criteria.where("projectId").is(projectId);

        Criteria criterias = new Criteria().andOperator(nameMatchCriteria, projectMatchCriteria);


        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criterias),
                Aggregation.project("authorName", "date", "commitScore"),
                Aggregation.group("date").count().as("numCommits")
                        .sum("commitScore").as("commitScore")
                        .addToSet("authorName").as("authorName")
                        .addToSet("date").as("date")
        );

        AggregationResults<CommitDateScore> groupResults = mongoTemplate.aggregate(aggregation, Commit.class, CommitDateScore.class);
        List<CommitDateScore> result = groupResults.getMappedResults();
        return result;
    }

}
