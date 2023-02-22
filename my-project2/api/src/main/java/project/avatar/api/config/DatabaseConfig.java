/*package project.avatar.api.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableMongoRepositories( basePackages = "project.avatar")
@EnableAsync
public class DatabaseConfig {

    @Bean
    public MongoClient mongoClient(){
        return new MongoClient("localhost");
    }
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoClientDbFactory(mongoClient(), "avatars");
    }
    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }
}*/
