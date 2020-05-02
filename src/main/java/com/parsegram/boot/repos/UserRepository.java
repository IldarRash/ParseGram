package com.parsegram.boot.repos;

import com.parsegram.boot.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);

    @Query("{ 'profile.email': ?0 }")
    Mono<User> findByEmail(String email);
}
