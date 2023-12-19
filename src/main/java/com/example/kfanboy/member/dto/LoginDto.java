package com.example.kfanboy.member.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(
	@Email @NotBlank @Length(min = 1, max = 50) String email,
	@NotBlank @Length(min = 1, max = 50) String password) {
}
