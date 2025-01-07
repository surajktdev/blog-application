package com.tech.blog.payloads;

import lombok.Builder;

@Builder
public record ApiResponse(String message, boolean success) {}
