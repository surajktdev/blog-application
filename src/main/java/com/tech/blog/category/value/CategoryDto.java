package com.tech.blog.category.value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryDto(
		Integer categoryId,
		@NotBlank @Size(min = 4, message = "Minimum size of category title is 4") String categoryTitle,
		@NotBlank @Size(min = 10, message = "Minimum size of category description is 4") String categoryDescription) {}
