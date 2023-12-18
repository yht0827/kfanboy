package com.example.kfanboy.member.dto;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record JoinDto(
	@Email @NotBlank String email, @NotBlank String password, @NotBlank String password2, @NotBlank String nickName) {
	public Member toEntity(String encryptPassword) {
		return Member.builder()
			.email(this.email())
			.password(encryptPassword)
			.nickName(this.nickName())
			.userRole(UserRole.ROLE_USER)
			.isDeleted(false)
			.build();
	}
}
