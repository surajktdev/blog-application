package com.tech.blog.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
	private int id;
	private String content;
}
