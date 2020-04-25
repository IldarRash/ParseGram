package com.parsegram.boot.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "yandex")
public class YandexApi {

    private String id;
    private String clientId;
    private String clientSecret;
    private String tokenType;
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
}
