package com.example.kfanboy.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDeleteRequestDto(@NotBlank String password) {
}
