package com.parsegram.boot.handlers;

import com.parsegram.boot.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RegistrationHandler {

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return convertUserToResponse(User
                                        .builder()
                                        .id(UUID.randomUUID().toString())
                                        .name("user name 1")
                                        .secret("secret")
                                        .build());
    }


    public static Mono<ServerResponse> convertUserToResponse(User user) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user, User.class);
    }
}
