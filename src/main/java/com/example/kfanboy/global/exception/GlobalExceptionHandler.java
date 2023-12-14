package com.example.kfanboy.global.exception;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.kfanboy.global.common.response.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponseDto> handleCustomException(final CustomException exception) {
		return error(exception);
	}

	/**
	 * Validation Exception
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponseDto> handleValidException(final MethodArgumentNotValidException exception) {
		return error(exception);
	}
}
