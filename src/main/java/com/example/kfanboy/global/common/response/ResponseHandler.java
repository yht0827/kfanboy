package com.example.kfanboy.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.exception.CustomException;

public class ResponseHandler {
	public static <T> ResponseEntity<HttpResponse<T>> success(final T data) {
		return ResponseEntity.ok()
			.body(HttpResponse.<T>builder()
				.status(HttpStatus.OK)
				.data(data)
				.build());
	}

	public static <T> ResponseEntity<HttpResponse<T>> success(final SuccessMessage successMessage) {
		return ResponseEntity.ok()
			.body(HttpResponse.<T>builder()
				.status(HttpStatus.OK)
				.message(successMessage.getMessage())
				.build());
	}

	public static ResponseEntity<HttpResponse<?>> success(final HttpStatus httpStatus,
		final SuccessMessage successMessage) {
		return ResponseEntity.status(httpStatus)
			.body(HttpResponse.builder()
				.status(httpStatus)
				.message(successMessage.getMessage())
				.build());
	}

	public static <T> ResponseEntity<HttpResponse<T>> success(final HttpStatus httpStatus,
		final T data) {
		return ResponseEntity.status(httpStatus)
			.body(HttpResponse.<T>builder()
				.status(httpStatus)
				.data(data)
				.build());

	}

	public static <T> ResponseEntity<HttpResponse<T>> success(final T data, final SuccessMessage successMessage) {
		return ResponseEntity.ok()
			.body(HttpResponse.<T>builder()
				.status(HttpStatus.OK)
				.message(successMessage.getMessage())
				.data(data)
				.build());
	}

	public static ResponseEntity<HttpResponse<?>> error(final CustomException exception) {
		return ResponseEntity.status(exception.getStatus())
			.body(HttpResponse.builder()
				.status(exception.getStatus())
				.message(exception.getMessage())
				.build());
	}

	public static ResponseEntity<HttpResponse<?>> error(final HttpStatus httpStatus, final String message) {
		return ResponseEntity.status(httpStatus)
			.body(HttpResponse.builder()
				.status(httpStatus)
				.message(message)
				.build());
	}

}
