package com.parsegram.boot.configs;

import com.parsegram.boot.handlers.YandexApiHandler;
import com.parsegram.boot.properties.YandexConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {


    @Bean
    RouterFunction<ServerResponse> registreFunction(YandexApiHandler handler) {
        return route()
                .GET("/yandex", accept(MediaType.APPLICATION_JSON), req -> handler.get(req))
                .GET("/oauth", accept(MediaType.APPLICATION_JSON), req -> handler.save(req))
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }


    @Bean
    WebClient yandexClient(YandexConfigProperties yandexConfigProperties) {
        return WebClient.builder()
                .baseUrl(yandexConfigProperties.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

   /* @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
        // ...
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        // ...
    }*/


}
