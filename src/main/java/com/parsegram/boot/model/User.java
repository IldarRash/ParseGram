package com.parsegram.boot.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "authClient")
public class User {

    @Id
    private String id;
    private String name;
    private String salt;
    private String secret;
}
