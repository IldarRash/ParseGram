package com.parsegram.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
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
