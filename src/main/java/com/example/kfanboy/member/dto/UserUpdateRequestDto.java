package com.example.kfanboy.member.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequestDto(
	@NotBlank Long id, @NotBlank String nickName, @NotBlank String password
) {
}
