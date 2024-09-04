package com.example.kfanboy.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentDeleteRequestDto(@NotNull Long commentId) {
}
