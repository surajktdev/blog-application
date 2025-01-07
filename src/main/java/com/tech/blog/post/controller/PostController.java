package com.tech.blog.post.controller;

import com.tech.blog.payloads.ApiResponse;
import com.tech.blog.post.service.PostService;
import com.tech.blog.post.value.AppConstants;
import com.tech.blog.post.value.PostDTO;
import com.tech.blog.post.value.PostPageResponse;
import com.tech.blog.post.value.PostResponse;
import com.tech.blog.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
@Tag(name = "Post Operations", description = "Endpoints for managing posts")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	@Operation(summary = "create new post")
	public Optional<PostResponse> createPost(
			@RequestBody PostDTO postDTO, @PathVariable Long userId, @PathVariable Integer categoryId) {
		PostResponse createdPost = postService.createPost(postDTO, userId, categoryId);
		return Optional.ofNullable(createdPost);
	}

	// update post
	@PatchMapping("post/{postId}")
	@Operation(summary = "update post data")
	public Optional<PostResponse> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {
		PostResponse updatePost = postService.updatePost(postDTO, postId);
		return Optional.ofNullable(updatePost);
	}

	// get single post
	@GetMapping("post/{postId}")
	@Operation(summary = "fetch single post by post ID")
	public ResponseEntity<PostResponse> getPostByPostId(@PathVariable Integer postId) {
		PostResponse postById = postService.getPostById(postId);
		return new ResponseEntity<>(postById, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("posts")
	@Operation(summary = "fetch all post")
	public ResponseEntity<PostPageResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
					Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostPageResponse allPost = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(allPost, HttpStatus.OK);
	}
	// get post by user
	@GetMapping("user/{userId}/posts")
	@Operation(summary = "fetch all posts by user ID")
	public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable Long userId) {
		List<PostResponse> postByUser = postService.getPostByUser(userId);
		return new ResponseEntity<>(postByUser, HttpStatus.OK);
	}

	// get post by user
	@GetMapping("category/{categoryId}/posts")
	@Operation(summary = "fetch all posts by category ID")
	public ResponseEntity<List<PostResponse>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostResponse> postByUser = postService.getPostByCategory(categoryId);
		return new ResponseEntity<>(postByUser, HttpStatus.OK);
	}

	@DeleteMapping("post/{postId}")
	@Operation(summary = "delete post")
	public ResponseEntity<ApiResponse> deletePostByPostId(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
	}

	@GetMapping("posts/search/{keywords}")
	@Operation(summary = "search posts using post title")
	public ResponseEntity<List<PostResponse>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostResponse> postResponses = postService.searchPost(keywords);
		return new ResponseEntity<>(postResponses, HttpStatus.OK);
	}

	// post image upload
	@PostMapping("post/image/upload/{postId}")
	@Operation(summary = "upload post image")
	public ResponseEntity<PostResponse> uploadPostImage(
			@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
		PostResponse existingPost = postService.getPostById(postId);
		String imageName = fileService.uploadImage(path, image);
		PostDTO postDTO = PostDTO.builder()
				.title(existingPost.title())
				.content(existingPost.content())
				.imageName(imageName)
				.build();
		System.out.println("image name= " + imageName);
		PostResponse updatePost = postService.updatePost(postDTO, postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}

	// method to serve files
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	@Operation(summary = "download image")
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
