package com.parsegram.boot.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Handler <T> {

    Mono<ServerResponse> get(ServerRequest request);

    Mono<ServerResponse> save(ServerRequest object);

    Flux<T> getAll();
}
