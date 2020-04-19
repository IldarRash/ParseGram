package com.parsegram.boot.services;

import com.parsegram.boot.model.YandexApi;
import com.parsegram.boot.properties.YandexConfigProperties;
import com.parsegram.boot.repos.YandexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class YandexService {
    private final YandexConfigProperties yandexConfigProperties;
    private final YandexRepository repo;

    public Mono<YandexApi> findYandexProps() {
        return repo.findById(yandexConfigProperties.getId());
    }
}
