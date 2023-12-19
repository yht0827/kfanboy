package com.example.kfanboy.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserUpdateRequestDto(@NotBlank String nickName, @NotBlank String password, @NotBlank String password2) {
}
