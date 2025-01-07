package com.tech.blog.post.service.impl;

import com.tech.blog.category.service.impl.CategoryServiceHandler;
import com.tech.blog.comment.CommentDTO;
import com.tech.blog.entities.Category;
import com.tech.blog.entities.Post;
import com.tech.blog.entities.User;
import com.tech.blog.exceptions.ResourceNotFoundException;
import com.tech.blog.post.service.PostService;
import com.tech.blog.post.value.PostDTO;
import com.tech.blog.post.value.PostPageResponse;
import com.tech.blog.post.value.PostResponse;
import com.tech.blog.repository.CategoryRepository;
import com.tech.blog.repository.PostRepository;
import com.tech.blog.repository.RoleRepository;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.user.service.impl.UserServiceHandler;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostResponse createPost(PostDTO postDTO, Long userId, Integer categoryId) {
		User user = userRepository
				.findById(Math.toIntExact(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		Category category = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", Long.valueOf(categoryId)));
		Post post = new Post();
		post.setTitle(postDTO.title());
		post.setContent(postDTO.content());
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		postRepository.save(post);
		return PostResponse.builder()
				.postId(post.getPostId())
				.title(post.getTitle())
				.content(post.getContent())
				.addedDate(post.getAddedDate())
				.upadtedDae(post.getUpdatedDate())
				.imageName(post.getImageName())
				.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
				.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
				.build();
	}

	@Override
	public PostResponse updatePost(PostDTO postDTO, Integer postId) {
		Post post = postRepository
				.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", Long.valueOf(postId)));

		post.setTitle(postDTO.title());
		post.setContent(postDTO.content());
		post.setImageName(postDTO.imageName());
		post.setUpdatedDate(new Date());
		postRepository.save(post);
		return PostResponse.builder()
				.postId(post.getPostId())
				.title(post.getTitle())
				.content(post.getContent())
				.addedDate(post.getAddedDate())
				.upadtedDae(post.getUpdatedDate())
				.imageName(post.getImageName())
				.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
				.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
				.build();
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository
				.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", Long.valueOf(postId)));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getPostById(Integer postId) {
		Post post = postRepository
				.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", Long.valueOf(postId)));
		Set<CommentDTO> commentDTOS = post.getComments().stream()
				.map(comment -> CommentDTO.builder()
						.id(comment.getId())
						.content(comment.getContent())
						.build())
				.collect(Collectors.toSet());
		return PostResponse.builder()
				.postId(post.getPostId())
				.title(post.getTitle())
				.content(post.getContent())
				.addedDate(post.getAddedDate())
				.upadtedDae(post.getUpdatedDate())
				.imageName(post.getImageName())
				.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
				.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
				.comments(commentDTOS)
				.build();
	}

	@Override
	public PostPageResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		//		Sort sort = null;
		//		if (sortDir.equalsIgnoreCase("asc")){
		//			sort= Sort.by(sortBy).ascending();
		//		}else {
		//			sort= Sort.by(sortBy).descending();
		//		}

		// I am writing above if else condition in one line using ternary operator
		Sort sort = (sortDir.equalsIgnoreCase("asc"))
				? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = postRepository.findAll(pageRequest);
		List<Post> allPost = pagePost.getContent();
		List<PostResponse> postResponses = allPost.stream()
				.map(post -> PostResponse.builder()
						.postId(post.getPostId())
						.title(post.getTitle())
						.content(post.getContent())
						.addedDate(post.getAddedDate())
						.upadtedDae(post.getUpdatedDate())
						.imageName(post.getImageName())
						.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
						.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
						.build())
				.toList();
		PostPageResponse pageResponse = PostPageResponse.builder()
				.content(postResponses)
				.pageNumber(pagePost.getNumber())
				.pageSize(pagePost.getSize())
				.totalElements(pagePost.getTotalElements())
				.totalPages(pagePost.getTotalPages())
				.lastPage(pagePost.isLast())
				.build();

		return pageResponse;
	}

	@Override
	public List<PostResponse> getPostByUser(Long userId) {
		User user = userRepository
				.findById(Math.toIntExact(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		List<Post> posts = postRepository.findByUser(user);
		return posts.stream()
				.map(post -> PostResponse.builder()
						.postId(post.getPostId())
						.title(post.getTitle())
						.content(post.getContent())
						.addedDate(post.getAddedDate())
						.upadtedDae(post.getUpdatedDate())
						.imageName(post.getImageName())
						.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
						.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
						.build())
				.toList();
	}

	@Override
	public List<PostResponse> getPostByCategory(Integer categoryId) {
		Category category = categoryRepository
				.findById(Math.toIntExact(categoryId))
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", Long.valueOf(categoryId)));
		List<Post> posts = postRepository.findByCategory(category);
		return posts.stream()
				.map(post -> PostResponse.builder()
						.postId(post.getPostId())
						.title(post.getTitle())
						.content(post.getContent())
						.addedDate(post.getAddedDate())
						.upadtedDae(post.getUpdatedDate())
						.imageName(post.getImageName())
						.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
						.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
						.build())
				.toList();
	}

	@Override
	public List<PostResponse> searchPost(String keyword) {
		List<Post> searchByTitle = postRepository.findByTitleContaining(keyword);

		//	modelMapper is not working
		//	List<PostResponse> list = byTitleContaining.stream().map(post -> modelMapper.map(post,
		// PostResponse.class)).toList();

		return searchByTitle.stream()
				.map(post -> PostResponse.builder()
						.postId(post.getPostId())
						.title(post.getTitle())
						.content(post.getContent())
						.addedDate(post.getAddedDate())
						.upadtedDae(post.getUpdatedDate())
						.imageName(post.getImageName())
						.category(new CategoryServiceHandler(categoryRepository).fromDBToResponse(post.getCategory()))
						.user(new UserServiceHandler(userRepository, roleRepository).userApiResponse(post.getUser()))
						.build())
				.toList();
	}
}
