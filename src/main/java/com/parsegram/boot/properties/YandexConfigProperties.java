package com.parsegram.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("yandex.oauth")
@Data
public class YandexConfigProperties {
    private String id;
    private String url;
    private String clientId;
    private String clientSecret;
}
