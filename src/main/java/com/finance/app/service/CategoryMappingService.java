package com.finance.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CategoryDetailsRequest;
import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateCategoryRequest;
import com.finance.app.dto.DeleteCategoryRequest;
import com.finance.app.dto.UpdateCategoryRequest;
import com.finance.app.entity.CategoryMapping;
import com.finance.app.repository.CategoryMappingRepository;

@Service
public class CategoryMappingService {

	@Autowired
	private CategoryMappingRepository repository;

	public CommonResponse createCategory(CreateCategoryRequest request) {

		CategoryMapping category = new CategoryMapping();

		category.setUserId(1L);
		category.setKeyword(request.getKeyword().toLowerCase());
		category.setCategory(request.getCategory().toUpperCase());
		category.setStatus("ACTIVE");

		boolean exists = repository.existsByUserIdAndKeywordAndStatus(1L, request.getKeyword().toLowerCase(), "ACTIVE");

		if (exists) {

			return new CommonResponse(false, "Keyword Already Exists", null);
		}
		repository.save(category);

		return new CommonResponse(true, "Category Created Successfully", category);
	}

	public CommonResponse getAllCategories() {

		return new CommonResponse(true, "Category List Fetched Successfully",
				repository.findByUserIdAndStatus(1L, "ACTIVE"));
	}

	public CommonResponse getCategoryDetails(CategoryDetailsRequest request) {

		CategoryMapping category = repository.findById(request.getCategoryId()).orElse(null);

		if (category == null) {

			return new CommonResponse(false, "Category Not Found", null);
		}

		return new CommonResponse(true, "Category Found", category);
	}

	public CommonResponse updateCategory(UpdateCategoryRequest request) {

		CategoryMapping category = repository.findById(request.getCategoryId()).orElse(null);

		if (category == null) {

			return new CommonResponse(false, "Category Not Found", null);
		}

		category.setKeyword(request.getKeyword().toLowerCase());

		category.setCategory(request.getCategory().toUpperCase());

		repository.save(category);

		return new CommonResponse(true, "Category Updated Successfully", category);
	}

	public CommonResponse deleteCategory(DeleteCategoryRequest request) {

		CategoryMapping category = repository.findById(request.getCategoryId()).orElse(null);

		if (category == null) {

			return new CommonResponse(false, "Category Not Found", null);
		}

		category.setStatus("DELETED");

		repository.save(category);

		return new CommonResponse(true, "Category Deleted Successfully", null);
	}
}