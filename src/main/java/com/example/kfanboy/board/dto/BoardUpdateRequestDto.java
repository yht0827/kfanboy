package com.example.kfanboy.board.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardUpdateRequestDto(
	@NotNull Long boardId, @NotBlank @Length(min = 1, max = 100) String title,
	@NotBlank String content, @NotNull Long categoryId) {
}
