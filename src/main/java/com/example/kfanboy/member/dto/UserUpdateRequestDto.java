package com.example.kfanboy.member.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequestDto(@NotBlank String nickName, @NotBlank String password, @NotBlank String password2) {
}
