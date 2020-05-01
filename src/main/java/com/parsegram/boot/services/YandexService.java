package com.parsegram.boot.services;

import com.parsegram.boot.model.YandexClient;
import com.parsegram.boot.model.dto.YandexResponse;
import com.parsegram.boot.properties.YandexConfigProperties;
import com.parsegram.boot.repos.YandexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class YandexService {
    private final YandexConfigProperties yandexConfigProperties;
    private final YandexRepository repo;
    private final WebClient webClient;

    public Mono<YandexClient> findYandexProps() {
        return repo.findById(yandexConfigProperties.getId());
    }

    public Mono<YandexClient> acceptToYandex(Mono<String> code) {
        WebClient client = WebClient.builder()
                                    .baseUrl(yandexConfigProperties.getUrl())
                                    .defaultHeader("Content-type", "application/x-www-form-urlencoded")
                                    .filter(ExchangeFilterFunctions
                                            .basicAuthentication(
                                                    yandexConfigProperties.getClientId(),
                                                    yandexConfigProperties.getClientSecret())
                                    )
                                    .build();
        return code.flatMap(co -> client.post()
                .uri("/token")
                .attribute("grant_type","authorization_code")
                .attribute("code", co)
                .exchange()
        )
        .log()
        .flatMap(clientResponse -> saveToken(clientResponse.bodyToMono(YandexResponse.class)));
    }

    private Mono<YandexClient> saveToken(Mono<YandexResponse> token) {
         return token.zipWith(repo.findById(yandexConfigProperties.getId()))
                .map(tuple -> {
                    YandexClient api = tuple.getT2();
                    YandexResponse response = tuple.getT1();

                    api.setAccessToken(response.getAccessToken());
                    api.setRefreshToken(response.getRefreshToken());
                    api.setExpiresIn(response.getExpiresIn());
                    api.setTokenType(response.getTokenType());
                    return api;
                })
                .flatMap(repo::save);
    }


}
