package com.example.kfanboy.member.dto;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserResponseDto(
	@NotBlank Long id, @Email String email, @NotBlank String nickName, @NotBlank UserRole userRole) {
	public static UserResponseDto toDto(Member member) {
		return UserResponseDto.builder()
			.id(member.getId())
			.email(member.getEmail())
			.nickName(member.getNickName())
			.userRole(member.getUserRole())
			.build();
	}
}
