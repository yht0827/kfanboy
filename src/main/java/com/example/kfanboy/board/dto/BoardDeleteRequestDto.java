package com.example.kfanboy.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardDeleteRequestDto(@NotNull Long boardId) {
}
