package com.tech.blog.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtAuthResponse {
	private String token;
	private String username;
}
