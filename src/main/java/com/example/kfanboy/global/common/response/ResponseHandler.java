package com.example.kfanboy.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

	public static ResponseEntity<ResponseDto<?>> error(final CustomException exception) {
		return ResponseEntity.status(exception.getStatus())
			.body(ResponseDto.builder()
				.httpStatus(exception.getStatus())
				.message(exception.getMessage())
				.build());
	}

	public static ResponseEntity<ResponseDto<?>> error(final HttpStatus httpStatus, final String message) {
		return ResponseEntity.status(httpStatus)
			.body(ResponseDto.builder()
				.httpStatus(httpStatus)
				.message(message)
				.build());
	}

}
