package com.tech.blog.post.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tech.blog.category.value.CategoryDto;
import com.tech.blog.comment.CommentDTO;
import com.tech.blog.user.value.UserResponse;
import java.util.Date;
import java.util.Set;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostResponse(
		Integer postId,
		String title,
		String content,
		String imageName,
		Date addedDate,
		Date upadtedDae,
		CategoryDto category,
		UserResponse user,
		Set<CommentDTO> comments) {
	//	public PostResponse updateImageName(String imageName){
	//		return new PostResponse(this.postId, this.title(), this.content(), imageName, this.addedDate(),
	// this.upadtedDae(),this.category(),this.user());
	//	}
}
