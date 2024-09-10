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
	USER_EXISTED(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
	USER_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 계정은 권한이 없습니다."),
	INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 서로 일치하지 않습니다."),
	INCORRECT_PASSWORD_OR_USER_EMAIL(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸거나, 해당 계정이 없습니다."),
	NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인 이후 사용 가능"),

	// Board
	BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 게시글입니다."),
	BOARD_WRITER_NOT_MATCHED(HttpStatus.BAD_REQUEST, "게시글 작성자가 아닙니다."),

	// Category
	CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),

	// Comment
	COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 댓글입니다."),
	COMMENT_WRITER_NOT_MATCHED(HttpStatus.BAD_REQUEST, "댓글 작성자가 아닙니다."),

	// Vote
	VOTE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 투표입니다."),
	VOTE_ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 투표 아이템입니다."),
	VOTE_ITEM_TOO_MANY(HttpStatus.BAD_REQUEST, "투표 아이템은 최대 10개 까지만 추가할 수 있습니다."),
	VOTE_USER_EXISTED(HttpStatus.BAD_REQUEST, "이미 투표한 사용자입니다."),
	VOTE_COUNT_LIMIT(HttpStatus.BAD_REQUEST, "최대 개수를 초과해서 더 이상 투표 할 수 없습니다."),
	VOTE_TIME_EXPIRED(HttpStatus.BAD_REQUEST, "투표 가능한 시간이 아닙니다."),

	DUMMY(null, "");

	private final HttpStatus status;
	private final String message;
}
