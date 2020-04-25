package com.parsegram.boot.services;

import com.parsegram.boot.model.YandexApi;
import com.parsegram.boot.properties.YandexConfigProperties;
import com.parsegram.boot.repos.YandexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class YandexService {
    private final YandexConfigProperties yandexConfigProperties;
    private final YandexRepository repo;
    private final WebClient webClient;

    public Mono<YandexApi> findYandexProps() {
        return repo.findById(yandexConfigProperties.getId());
    }

    public Mono<YandexApi> acceptToYandex(Mono<String> code) {
        return code.flatMap(co -> {
            return webClient.post()
                    .uri(URI.create("/token"))
                    .attribute("grant_type","authorization_code")
                    .attribute("code", co)
                    .exchange();
        })
        .log()
        .flatMap(clientResponse -> {
            return saveToken(clientResponse.bodyToMono(String.class));
        });
    }

    private Mono<YandexApi> saveToken(Mono<String> token) {
         return token.zipWith(repo.findById(yandexConfigProperties.getId()))
                .map(tuple -> {
                    YandexApi api = tuple.getT2();
                    api.setToken(tuple.getT1());
                    return api;
                })
                .flatMap(repo::save);
    }


}
