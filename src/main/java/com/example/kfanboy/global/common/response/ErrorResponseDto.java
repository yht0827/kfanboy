package com.example.kfanboy.global.common.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import lombok.Builder;

@Builder
public record ErrorResponseDto(HttpStatus httpStatus, String message, List<CustomFieldError> errorList) {
	public record CustomFieldError(String field, String value, String reason) {
		public static List<CustomFieldError> of(final BindingResult bindingResult) {
			return bindingResult.getFieldErrors().stream()
				.map(error -> new CustomFieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}
	}
}
