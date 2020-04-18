package com.parsegram.boot.repos;

import com.parsegram.boot.model.YandexApi;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YandexRepo extends ReactiveMongoRepository<YandexApi, String> {
}
