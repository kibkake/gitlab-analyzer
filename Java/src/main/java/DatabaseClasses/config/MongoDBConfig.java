package main.java.DatabaseClasses.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {
    private final static String mongoDBConnectionAddress = "mongodb+srv://Kae:mongopass@plutocluster.nop8i.mongodb.net/gitlab?retryWrites=true&w=majority";


    public MongoClient mongoClient() {
        return MongoClients.create(mongoDBConnectionAddress);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "test");
    }
}

