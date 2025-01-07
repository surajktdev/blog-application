package com.tech.blog.user.controller;

import com.tech.blog.payloads.ApiResponse;
import com.tech.blog.user.service.UserService;
import com.tech.blog.user.value.UserRecord;
import com.tech.blog.user.value.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Operations", description = "Endpoints for handling user-related functionalities")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	@Operation(summary = "create new user")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRecord userRecord) {
		UserResponse createdUser = this.userService.registerNewUser(userRecord);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	@Operation(summary = "update user details")
	public ResponseEntity<UserResponse> updateUser(
			@Valid @RequestBody UserRecord userRecord, @PathVariable Long userId) {
		UserResponse updatedUser = this.userService.updateUser(userRecord, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// Only ADMIN assess
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	@Operation(summary = "delete user")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
		// return new ResponseEntity<>(Map.of("message", "User Deleted Successfully"), HttpStatus.OK);
		return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}

	@GetMapping("/")
	@Operation(summary = "fetch all user details")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/{userId}")
	@Operation(summary = "fetch single user details")
	public ResponseEntity<UserResponse> getSingleUser(@PathVariable Long userId) {
		return ResponseEntity.ok(this.userService.getUserByUserId(userId));
	}
}
