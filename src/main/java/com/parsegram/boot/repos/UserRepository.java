package com.parsegram.boot.repos;

import com.parsegram.boot.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {

    Mono<User> findByUsername(String username);
}
