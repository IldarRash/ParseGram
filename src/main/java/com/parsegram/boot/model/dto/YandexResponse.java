package com.parsegram.boot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class YandexResponse {

    private String tokenType;
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
}
