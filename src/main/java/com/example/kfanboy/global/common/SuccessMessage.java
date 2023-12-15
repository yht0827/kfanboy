package com.example.kfanboy.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

	//Common
	OK("성공"),
	CREATED("리소스가 생성됨"),

	// User
	LOGIN_SUCCESS("로그인 성공"),
	LOGOUT_SUCCESS("로그 아웃 성공"),
	AVAILABLE_EMAIL("사용 가능한 이메일"),
	CREATE_USER_SUCCESS("계정 생성 성공"),
	DELETE_USER_SUCCESS("계정 삭제 성공"),

	DUMMY("");

	private final String message;
}
