package com.tech.blog.user.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.tech.blog.repository.RoleRepository;
import com.tech.blog.repository.UserRepository;

class UserServiceHandlerTest {

	private final UserRepository userRepository = mock(UserRepository.class);

	private final RoleRepository roleRepository = mock(RoleRepository.class);

	UserServiceHandler userServiceHandler = new UserServiceHandler(userRepository, roleRepository);
}
