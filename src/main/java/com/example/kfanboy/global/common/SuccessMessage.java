package com.example.kfanboy.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

	//Common
	OK("요청 성공"),
	CREATE("리소스 작성 성공"),

	// User
	LOGIN_SUCCESS("로그인 성공"),
	LOGOUT_SUCCESS("로그 아웃 성공"),
	AVAILABLE_EMAIL("사용 가능한 이메일"),
	UPDATE_USER_SUCCESS("계정 업데이트 성공"),
	DELETE_USER_SUCCESS("계정 삭제 성공"),

	// Board
	UPDATE_BOARD_SUCCESS("게시글 업데이트 성공"),
	DELETE_BOARD_SUCCESS("게시글 삭제 성공"),

	// Like
	UPDATE_LIKE_SUCCESS("좋아요 업데이트 성공"),

	DUMMY("");

	private final String message;
}
