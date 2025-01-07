package com.tech.blog.post.value;

import lombok.Builder;

@Builder
public record PostDTO(String title, String content, String imageName) {}
