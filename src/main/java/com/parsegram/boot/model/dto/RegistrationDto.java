package com.parsegram.boot.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonAutoDetect
public class RegistrationDto implements Serializable {
    private String username;
    private String email;
    private String password;
    private String phone;
}
