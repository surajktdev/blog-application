package com.tech.blog.post.value;

import java.util.List;
import lombok.Builder;

@Builder
public record PostPageResponse(
		List<PostResponse> content,
		int pageNumber,
		int pageSize,
		Long totalElements,
		int totalPages,
		boolean lastPage) {}
