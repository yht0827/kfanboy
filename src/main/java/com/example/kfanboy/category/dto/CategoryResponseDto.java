package com.example.kfanboy.category.dto;

import com.example.kfanboy.category.domain.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryResponseDto(@NotNull Long categoryId, @NotBlank String categoryName) {
	public static CategoryResponseDto toDto(Category category) {
		return CategoryResponseDto.builder()
			.categoryId(category.getCategoryId())
			.categoryName(category.getCategoryName())
			.build();
	}
}
