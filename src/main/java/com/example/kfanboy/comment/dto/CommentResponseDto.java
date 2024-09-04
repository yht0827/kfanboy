package com.example.kfanboy.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentResponseDto(
	@NotNull Long commentId, @NotNull String content, @NotNull Boolean isDeleted,
	@NotNull Long commentGroup, @NotNull Long commentLevel) {
}
