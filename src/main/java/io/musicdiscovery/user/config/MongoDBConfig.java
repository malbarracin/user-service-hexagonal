package io.musicdiscovery.user.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
@EnableMongoRepositories
public class MongoDBConfig extends AbstractReactiveMongoConfiguration {


    @Value("${mongo.data-base-user-name}")
    private String DATA_BASE_USER_NAME;

    @Value("${mongo.data-base-user-password}")
    private String DATA_BASE_USER_PASSWORD;

    @Value("${mongo.data-base-host}")
    private String DATA_BASE_HOST;

    @Value("${mongo.data-base-name}")
    private String DATA_BASE_NAME;

    @Override
    protected String getDatabaseName() {
        return DATA_BASE_NAME;
    }


    @Override
    @Bean
    public MongoClient reactiveMongoClient() {

        ConnectionString connectionString = new ConnectionString("mongodb://"+DATA_BASE_USER_NAME+":"+DATA_BASE_USER_PASSWORD+"@"+DATA_BASE_HOST);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder.maxWaitTime(5000, TimeUnit.MILLISECONDS)) // Tiempo de espera de conexión
                .applyToSocketSettings(builder -> builder.connectTimeout(5000, TimeUnit.MILLISECONDS)) // Tiempo de espera de socket
                .build();

        return MongoClients.create(settings);
    }


    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(new SimpleReactiveMongoDatabaseFactory(mongoClient, "database"));
    }
}
