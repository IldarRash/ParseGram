package com.parsegram.boot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "yandex")
public class YandexClient {

    public YandexClient() {
        id = UUID.randomUUID();
    }

    @Id
    private UUID id;
    private String tokenType;
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
}
