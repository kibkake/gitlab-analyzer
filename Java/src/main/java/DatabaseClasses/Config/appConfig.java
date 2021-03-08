package main.java.DatabaseClasses.Config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@EnableMongoAuditing
@EnableAutoConfiguration
@Configuration
public class appConfig {
    private static String database = "gitlab";
    private final static String mongoDBConnectionAddress = "mongodb+srv://Kae:mongopass@plutocluster.nop8i.mongodb.net/"
        + database + "?retryWrites=true&w=majority";

    public MongoClient mongoClient() {
        return MongoClients.create(mongoDBConnectionAddress);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), database);
    }
}

