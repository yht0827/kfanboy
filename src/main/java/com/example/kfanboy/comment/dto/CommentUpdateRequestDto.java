package com.example.kfanboy.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentUpdateRequestDto(@NotNull Long commentId, @NotBlank String content) {
}
