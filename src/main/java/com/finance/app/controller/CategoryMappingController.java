package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.app.dto.CategoryDetailsRequest;
import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateCategoryRequest;
import com.finance.app.dto.DeleteCategoryRequest;
import com.finance.app.dto.UpdateCategoryRequest;
import com.finance.app.service.CategoryMappingService;

@RestController
@RequestMapping("/api/category")
public class CategoryMappingController {

    @Autowired
    private CategoryMappingService service;

    @PostMapping("/create")
    public CommonResponse createCategory(
            @RequestBody CreateCategoryRequest request) {

        return service.createCategory(request);
    }

    @PostMapping("/list")
    public CommonResponse getAllCategories() {

        return service.getAllCategories();
    }

    @PostMapping("/details")
    public CommonResponse getCategoryDetails(
            @RequestBody CategoryDetailsRequest request) {

        return service.getCategoryDetails(request);
    }

    @PostMapping("/update")
    public CommonResponse updateCategory(
            @RequestBody UpdateCategoryRequest request) {

        return service.updateCategory(request);
    }

    @PostMapping("/delete")
    public CommonResponse deleteCategory(
            @RequestBody DeleteCategoryRequest request) {

        return service.deleteCategory(request);
    }
}