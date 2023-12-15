package com.example.kfanboy.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

	// Common
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),
	DUPLICATION(HttpStatus.CONFLICT, "중복된 값"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "존재 하지 않는 값"),
	FAIL(HttpStatus.CONFLICT, "실패"),
	INPUT_ERROR(HttpStatus.BAD_REQUEST, "입력 값 오류"),
	NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 리소스에 대한 접근 권한이 존재 하지 않습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류"),

	// User
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
	USER_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 계정은 권한이 없습니다."),
	INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 서로 일치하지 않습니다."),
	INCORRECT_PASSWORD_OR_USER_EMAIL(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸거나, 해당 계정이 없습니다."),
	DUPLICATE_ACCOUNT_USER(HttpStatus.UNAUTHORIZED, "해당 계정이 존재합니다."),
	USER_NOT_REGISTERED(HttpStatus.UNAUTHORIZED, "등록되지 않은 계정입니다."),
	USER_NOT_CREATED(HttpStatus.BAD_REQUEST, "계정을 생성하지 못하였습니다."),
	NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인 이후 사용 가능"),

	DUMMY(null, "");

	private final HttpStatus status;
	private final String message;
}
