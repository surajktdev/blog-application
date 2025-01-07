package com.tech.blog.comment;

import com.tech.blog.payloads.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Tag(name = "Comment Operations", description = "Endpoints for managing posts")
public class CommentController {

	@Autowired
	private CommentHandler commentHandler;

	@PostMapping("post/{postId}/comments")
	@Operation(summary = "add comments for post")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment, @PathVariable Integer postId) {
		CommentDTO createComment = commentHandler.createComment(comment, postId);
		return new ResponseEntity<>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("comment/{commentId}")
	@Operation(summary = "delete comment")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		commentHandler.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
	}
}
