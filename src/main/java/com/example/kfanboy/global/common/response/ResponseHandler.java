package com.example.kfanboy.global.common.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.exception.CustomException;

import jakarta.validation.ConstraintViolationException;

public class ResponseHandler {

	public static <T> ResponseEntity<PageResponseDto<T>> success(PageResponseDto<T> pageResponseDto) {
		return ResponseEntity.ok().body(pageResponseDto);
	}

	public static <T> ResponseEntity<ResponseDto<T>> success(final T data) {
		return ResponseEntity.ok()
			.body(ResponseDto.<T>builder()
				.message(SuccessMessage.OK.getMessage())
				.data(data)
				.build());
	}

	public static ResponseEntity<ResponseDto<?>> success(final SuccessMessage successMessage) {
		return ResponseEntity.ok()
			.body(ResponseDto.builder().message(successMessage.getMessage()).build());
	}

	public static <T> ResponseEntity<ResponseDto<T>> success(final T data, final SuccessMessage successMessage) {
		return ResponseEntity.ok()
			.body(ResponseDto.<T>builder()
				.message(successMessage.getMessage())
				.data(data)
				.build());
	}

	public static ResponseEntity<ResponseDto<?>> create(final Long id) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ResponseDto.builder()
				.data(CreateDto.builder().id(id).build())
				.message(SuccessMessage.CREATE.getMessage())
				.build());
	}

	public static ResponseEntity<ErrorResponseDto> error(final CustomException exception) {
		return ResponseEntity.status(exception.getStatus())
			.body(ErrorResponseDto.builder()
				.message(exception.getMessage())
				.errorList(List.of())
				.build());
	}

	public static ResponseEntity<ErrorResponseDto> error(final Exception exception) {
		if (exception instanceof MethodArgumentNotValidException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponseDto.builder()
					.message(exception.getMessage())
					.errorList(ErrorResponseDto.CustomFieldError.of(
						((MethodArgumentNotValidException)exception).getBindingResult()))
					.build());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponseDto.builder()
				.message(exception.getMessage())
				.errorList(ErrorResponseDto.CustomFieldError.of(
					((ConstraintViolationException)exception).getConstraintViolations()))
				.build());
	}
}
