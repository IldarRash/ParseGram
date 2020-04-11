package com.parsegram.boot.config;

import com.parsegram.boot.handlers.RegistrationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig {


    @Bean
    RouterFunction<ServerResponse> registreFunction(RegistrationHandler registrationHandler) {
        return route()
                .GET("/user/", accept(MediaType.APPLICATION_JSON), registrationHandler::createUser)
                .build();
    }
}
