package com.example.kfanboy.member.dto;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserResponseDto(
	@NotNull Long memberId, @Email String email, @NotBlank String nickName, @NotBlank UserRole userRole) {
	public static UserResponseDto toDto(Member member) {
		return UserResponseDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.nickName(member.getNickName())
			.userRole(member.getUserRole())
			.build();
	}
}
