package com.example.kfanboy.member.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteRequestDto(@NotBlank Long id, @NotBlank String password) {
}
