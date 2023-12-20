package com.example.kfanboy.global.member;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.entity.UserRole;
import com.example.kfanboy.member.dto.JoinDto;
import com.example.kfanboy.member.dto.LoginDto;
import com.example.kfanboy.member.dto.UserDeleteRequestDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.member.dto.UserUpdateRequestDto;

public class MemberDtoTest {

	public static JoinDto getJoinDto() {
		return JoinDto.builder()
			.email("user1@naver.com")
			.password("Qwe123!@#")
			.password2("Qwe123!@#")
			.nickName("user1")
			.build();
	}

	public static UserResponseDto getUserResponseDto() {
		return UserResponseDto.builder()
			.id(1L)
			.email("test123@naver.com")
			.nickName("test123")
			.userRole(UserRole.ROLE_USER)
			.build();
	}

	public static LoginDto getLoginDto() {
		return LoginDto.builder()
			.email("test1234@naver.com")
			.password("Qwe123!@#")
			.build();
	}

	public static UserUpdateRequestDto getUserUpdateRequestDto() {
		return UserUpdateRequestDto.builder()
			.nickName("test123")
			.password("Qwe123!@#")
			.password2("Qwe123!@#")
			.build();
	}

	public static UserDeleteRequestDto getUserDeleteRequestDto() {
		return UserDeleteRequestDto.builder()
			.password("Qwe123!@#")
			.build();
	}

	public static Member getMember() {
		return Member.builder()
			.id(1L)
			.email("test1234@naver.com")
			.password("Qwe123!@#")
			.nickName("test1234")
			.isDeleted(false)
			.userRole(UserRole.ROLE_USER)
			.build();
	}

}
