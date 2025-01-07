package com.tech.blog.user.service;

import com.tech.blog.user.value.UserRecord;
import com.tech.blog.user.value.UserResponse;
import java.util.List;

public interface UserService {

	UserResponse registerNewUser(UserRecord userRecord);

	//	UserResponse createUser(UserRecord userRecord);

	UserResponse updateUser(UserRecord userRecord, Long userId);

	UserResponse getUserByUserId(Long userId);

	List<UserResponse> getAllUsers();

	void deleteUser(Long userId);
}
