package com.parsegram.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    private UUID id;
    private String email;
    private Date createAt;
    private YandexClient yandexClient;
    private List<Subscribe> subscribes;
}
