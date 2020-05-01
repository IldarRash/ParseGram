package com.parsegram.boot.repos;

import com.parsegram.boot.model.YandexClient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YandexRepository extends ReactiveMongoRepository<YandexClient, String> {
}
