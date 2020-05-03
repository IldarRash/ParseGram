package com.parsegram.boot.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AuthResponse {
	private String token;
	private String username;
	private String error;
}