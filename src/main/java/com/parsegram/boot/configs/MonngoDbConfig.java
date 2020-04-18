package com.parsegram.boot.configs;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.parsegram.boot.repos")
public class MonngoDbConfig extends AbstractReactiveMongoConfiguration {
    @Override
    public MongoClient reactiveMongoClient() {
        return mongoClient();
    }

    @Override
    protected String getDatabaseName() {
        return "instagramm";
    }

    @Bean
    public MongoClient mongoClient() {
        var mongoSettings = MongoClientSettings.builder()
                .credential(MongoCredential.createCredential("root", "admin", "example".toCharArray()))
                .build();
        return MongoClients.create(mongoSettings);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }
}