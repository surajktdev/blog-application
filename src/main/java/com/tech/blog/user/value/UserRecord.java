package com.tech.blog.user.value;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserRecord(
		//	Long userId,
		@NotEmpty(message = "Name is mandatory") @Size(min = 4, message = "Username must be min of 4 characters!!")
				String userName,
		@NotEmpty(message = "Email is mandatory")
				@Email(message = "Email address is not valid")
				@Pattern(regexp = ".+@.+\\..+", message = "pattern is not correct it should be like example@gmail.com")
				String email,
		@NotEmpty(message = "Password is mandatory")
				@Size(min = 3, max = 10, message = "Password must be min of 3 chars and max 10 chars!!")
				String password,
		String about,
		LocalDateTime createdDate,
		LocalDateTime updatedDate) {}
