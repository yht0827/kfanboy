package com.example.kfanboy.member.dto;

import jakarta.validation.constraints.NotBlank;


public record UserDeleteRequestDto(@NotBlank String password) {
}
