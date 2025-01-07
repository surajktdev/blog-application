package com.tech.blog.post.service;

import com.tech.blog.post.value.PostDTO;
import com.tech.blog.post.value.PostPageResponse;
import com.tech.blog.post.value.PostResponse;
import java.util.List;

public interface PostService {
	// create
	PostResponse createPost(PostDTO postDTO, Long userId, Integer categoryId);
	// update
	PostResponse updatePost(PostDTO postDTO, Integer postId);
	// delete
	void deletePost(Integer postId);
	// get all post
	PostPageResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	// get single post
	PostResponse getPostById(Integer postId);
	// get post by user
	List<PostResponse> getPostByUser(Long userId);
	// get post by category
	List<PostResponse> getPostByCategory(Integer categoryId);
	// search posts
	List<PostResponse> searchPost(String keyword);
}
