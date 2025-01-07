package com.tech.blog.user.service.impl;

import com.tech.blog.entities.Role;
import com.tech.blog.entities.User;
import com.tech.blog.exceptions.ResourceNotFoundException;
import com.tech.blog.post.value.AppConstants;
import com.tech.blog.repository.RoleRepository;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.user.service.UserService;
import com.tech.blog.user.value.UserRecord;
import com.tech.blog.user.value.UserResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserResponse registerNewUser(UserRecord userRecord) {
		User user = userRecordToUserDB(userRecord);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User save = this.userRepository.save(user);

		return userApiResponse(save);
	}

	//	@Override
	//	public UserResponse createUser(UserRecord userRecord) {
	//		User user = userRecordToUserDB(userRecord);
	//		userRepository.save(user);
	//		return userApiResponse(user);
	//	}

	@Override
	public UserResponse updateUser(UserRecord userRecord, Long userId) {
		// find the particular value which you want to update
		User user = userRepository
				.findById(Math.toIntExact(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		// set value which you want to update
		user.setName(userRecord.userName());
		user.setEmail(userRecord.email());
		user.setPassword(userRecord.password());
		user.setAbout(userRecord.about());

		// and now save the updated value
		User updateUser = userRepository.save(user);

		return userApiResponse(updateUser);
	}

	@Override
	public UserResponse getUserByUserId(Long userId) {
		User user = userRepository
				.findById(Math.toIntExact(userId))
				.orElseThrow((() -> new ResourceNotFoundException("User", "id", userId)));
		return userApiResponse(user);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> allUser = (List<User>) userRepository.findAll();
		return allUser.stream().map(this::userApiResponse).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository
				.findById(Math.toIntExact(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id", userId));
		userRepository.delete(user);
	}

	public User userRecordToUserDB(UserRecord record) {
		User userData = new User();
		userData.setName(record.userName());
		userData.setEmail(record.email());
		userData.setPassword(record.password());
		userData.setAbout(record.about());
		userData.setCreatedDate(LocalDateTime.now());
		userData.setUpdatedDate(LocalDateTime.now());
		return userData;
	}

	public UserResponse userApiResponse(User user) {
		return UserResponse.builder()
				.userId(user.getId())
				.userName(user.getName())
				.email(user.getEmail())
				.password(user.getPassword())
				.about(user.getAbout())
				.roles(user.getRoles())
				.createdDate(user.getCreatedDate())
				.updatedDate(user.getUpdatedDate())
				.build();
	}
}
