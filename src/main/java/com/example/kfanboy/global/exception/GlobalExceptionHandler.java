package com.example.kfanboy.global.exception;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.kfanboy.global.common.response.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ResponseDto<?>> handleCustomException(final CustomException exception) {
		return error(exception);
	}

	/**
	 * Validation Exception
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ResponseDto<?>> handleValidException(
		final MethodArgumentNotValidException exception) {
		FieldError error = exception.getBindingResult().getFieldErrors().get(0);
		return error(HttpStatus.BAD_REQUEST, error.getField() + " : " + error.getDefaultMessage());
	}
}
