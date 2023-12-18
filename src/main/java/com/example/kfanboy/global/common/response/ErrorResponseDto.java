package com.example.kfanboy.global.common.response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import jakarta.validation.ConstraintViolation;
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

		public static List<CustomFieldError> of(final Set<ConstraintViolation<?>> constraintViolations) {
			return constraintViolations.stream().map(
					constraintViolation -> new CustomFieldError(
						constraintViolation.getPropertyPath() == null ? "" :
							constraintViolation.getPropertyPath().toString(),
						constraintViolation.getInvalidValue() == null ? "" :
							constraintViolation.getInvalidValue().toString(),
						constraintViolation.getMessage()))
				.collect(Collectors.toList());
		}

	}
}
