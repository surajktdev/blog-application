package com.tech.blog.category.service.impl;

import com.tech.blog.category.service.CategoryService;
import com.tech.blog.category.value.CategoryDto;
import com.tech.blog.entities.Category;
import com.tech.blog.exceptions.ResourceNotFoundException;
import com.tech.blog.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceHandler implements CategoryService {

	@Autowired
	private final CategoryRepository categoryRepository;

	//	@Autowired
	//	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// I need to check why this model mapper is not working
		// Category category = this.modelMapper.map(categoryDto, Category.class);
		// Category save = this.categoryRepository.save(category);
		// return this.modelMapper.map(save, CategoryDto.class);

		Category category = saveToDB(categoryDto);
		categoryRepository.save(category);
		return fromDBToResponse(category);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// find the particular value which you want to update
		Category category = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", Long.valueOf(categoryId)));

		// set value which you want to update
		category.setCategoryTitle(categoryDto.categoryTitle());
		category.setCategoryDescription(categoryDto.categoryDescription());

		// and now save the updated value
		Category updatedCategory = categoryRepository.save(category);
		return fromDBToResponse(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", Long.valueOf(categoryId)));
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", Long.valueOf(categoryId)));

		return fromDBToResponse(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allRecord = categoryRepository.findAll();
		return allRecord.stream().map(this::fromDBToResponse).toList();
	}

	public Category saveToDB(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCategoryTitle(categoryDto.categoryTitle());
		category.setCategoryDescription(categoryDto.categoryDescription());
		return category;
	}

	public CategoryDto fromDBToResponse(Category category) {
		CategoryDto categoryDto = CategoryDto.builder()
				.categoryId(category.getCategoryId())
				.categoryTitle(category.getCategoryTitle())
				.categoryDescription(category.getCategoryDescription())
				.build();
		return categoryDto;
	}
}
