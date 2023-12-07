package com.example.kfanboy.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

	private final HttpStatus status;
	private final String message;

	public CustomException(ErrorMessage errorMessage) {
		this.status = errorMessage.getStatus();
		this.message = errorMessage.getMessage();
	}
}
