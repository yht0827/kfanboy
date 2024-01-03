package com.example.kfanboy.like.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LikeRequestDto(@NotNull Long boardId) {
}
