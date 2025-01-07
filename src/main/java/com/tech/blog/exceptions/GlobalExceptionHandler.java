package com.tech.blog.exceptions;

import com.tech.blog.payloads.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException exceptions) {
		String meString = exceptions.getMessage();
		ApiResponse response = new ApiResponse(meString, false);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(
			MethodArgumentNotValidException notValidException) {
		Map<String, String> response = new HashMap<>();
		notValidException.getBindingResult().getAllErrors().forEach((error) -> {
			String errorFieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			response.put(errorFieldName, errorMessage);
		});
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
