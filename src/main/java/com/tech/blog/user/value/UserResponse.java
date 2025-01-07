package com.tech.blog.user.value;

import com.tech.blog.entities.Role;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record UserResponse(
		Long userId,
		String userName,
		String email,
		String password,
		String about,
		Set<Role> roles,
		LocalDateTime createdDate,
		LocalDateTime updatedDate) {}
