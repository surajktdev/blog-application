package com.tech.blog.comment;

import com.tech.blog.entities.Comment;
import com.tech.blog.entities.Post;
import com.tech.blog.exceptions.ResourceNotFoundException;
import com.tech.blog.repository.CommentRepository;
import com.tech.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentHandler {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		Post post = postRepository
				.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", Long.valueOf(postId)));

		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		comment.setPost(post);
		Comment saveComment = commentRepository.save(comment);
		return CommentDTO.builder()
				.id(saveComment.getId())
				.content(saveComment.getContent())
				.build();
	}

	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository
				.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", Long.valueOf(commentId)));

		commentRepository.delete(comment);
	}
}
