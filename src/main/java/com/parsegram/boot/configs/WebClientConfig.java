package com.parsegram.boot.configs;

import com.parsegram.boot.properties.YandexConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient yandexClient(YandexConfigProperties yandexConfigProperties) {
        return WebClient.builder().baseUrl(yandexConfigProperties.getUrl()).build();
    }

}
