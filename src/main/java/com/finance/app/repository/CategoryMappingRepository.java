package com.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.CategoryMapping;

public interface CategoryMappingRepository
        extends JpaRepository<CategoryMapping, Long> {
	
	boolean existsByUserIdAndKeywordAndStatus(
	        Long userId,
	        String keyword,
	        String status);

    List<CategoryMapping> findByUserIdAndStatus(
            Long userId,
            String status);

}