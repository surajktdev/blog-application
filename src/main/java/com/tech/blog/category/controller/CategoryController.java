package com.tech.blog.category.controller;

import static org.springframework.http.HttpStatus.*;

import com.tech.blog.category.service.CategoryService;
import com.tech.blog.category.value.CategoryDto;
import com.tech.blog.payloads.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Operations", description = "Endpoints for handling category-related functionalities")
public class CategoryController {

	@Autowired
	private CategoryService service;

	// create
	@PostMapping("/")
	@Operation(summary = "create new category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto category = service.createCategory(categoryDto);
		return new ResponseEntity<>(category, CREATED);
	}
	// update
	@PutMapping("/{catId}")
	@Operation(summary = "update category")
	public ResponseEntity<CategoryDto> updateCategory(
			@PathVariable Integer catId, @Valid @RequestBody CategoryDto categoryDto) {
		// TODO: process PUT request
		CategoryDto updateCategory = service.updateCategory(categoryDto, catId);
		return new ResponseEntity<>(updateCategory, OK);
	}
	// delete
	@DeleteMapping("/{catId}")
	@Operation(summary = "delete category")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		service.deleteCategory(catId);
		return new ResponseEntity<>(new ApiResponse("category is deleted successfully!!", true), OK);
	}
	// getSingle
	@GetMapping("/{catId}")
	@Operation(summary = "fetch single category")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		CategoryDto getCategory = service.getCategory(catId);
		return new ResponseEntity<>(getCategory, OK);
	}

	// getAll
	@GetMapping("/")
	@Operation(summary = "fetch all category")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> getCategories = service.getAllCategory();
		return ResponseEntity.ok(getCategories);
	}
}
