package com.parsegram.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
public class ServerRequestResponseConverter<T> {

    final Class<T> entityClass;

    {
        Type type = this.getClass().getGenericSuperclass();
        try {
            entityClass = (Class<T>) ((type instanceof ParameterizedType) ? ((ParameterizedType) type).getActualTypeArguments()[0] : type);
        } catch (ClassCastException ex) {
            log.error("Cannot define entity class", ex);
            throw new RuntimeException(ex);
        }
    }

    public Mono<ServerResponse> convertBodyToResponse(Mono<T> body) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(body, entityClass);
    }
}
