package com.tech.blog.security;

import lombok.Data;

@Data
public class JwtAuthRequest {
	private String email; // username
	private String password;
}
