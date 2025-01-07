package com.tech.blog.category.service;

import com.tech.blog.category.value.CategoryDto;
import java.util.List;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	void deleteCategory(Integer categoryId);

	// get
	CategoryDto getCategory(Integer categoryId);

	// getAll
	List<CategoryDto> getAllCategory();
}
