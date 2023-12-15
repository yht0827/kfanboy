package com.example.kfanboy.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.exception.CustomException;

public class ResponseHandler {
	public static <T> ResponseEntity<ResponseDto<T>> success(final T data) {
		return ResponseEntity.ok()
			.body(ResponseDto.<T>builder()
				.httpStatus(HttpStatus.OK)
				.message(SuccessMessage.OK.getMessage())
				.data(data)
				.build());
	}

	public static <T> ResponseEntity<ResponseDto<T>> success(final SuccessMessage successMessage) {
		return ResponseEntity.ok()
			.body(ResponseDto.<T>builder()
				.httpStatus(HttpStatus.OK)
				.message(successMessage.getMessage())
				.build());
	}

	public static <T> ResponseEntity<ResponseDto<T>> success(final HttpStatus httpStatus, final T data) {
		return ResponseEntity.status(httpStatus)
			.body(ResponseDto.<T>builder()
				.httpStatus(httpStatus)
				.data(data)
				.build());

	}

	public static <T> ResponseEntity<ResponseDto<T>> success(final T data, final SuccessMessage successMessage) {
		return ResponseEntity.ok()
			.body(ResponseDto.<T>builder()
				.httpStatus(HttpStatus.OK)
				.message(successMessage.getMessage())
				.data(data)
				.build());
	}

	public static <T> ResponseEntity<ResponseDto<T>> success(final HttpStatus httpStatus, final T data,
		final SuccessMessage successMessage) {
		return ResponseEntity.status(httpStatus)
			.body(ResponseDto.<T>builder()
				.httpStatus(httpStatus)
				.message(successMessage.getMessage())
				.data(data)
				.build());
	}

	public static ResponseEntity<ErrorResponseDto> error(final CustomException exception) {
		return ResponseEntity.status(exception.getStatus())
			.body(ErrorResponseDto.builder()
				.httpStatus(exception.getStatus())
				.message(exception.getMessage())
				.build());
	}

	public static ResponseEntity<ErrorResponseDto> error(final MethodArgumentNotValidException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponseDto.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(exception.getMessage())
				.errorList(ErrorResponseDto.CustomFieldError.of(exception.getBindingResult()))
				.build());
	}

}
