package com.example.kfanboy.global.common.response;

import lombok.Builder;

public record ResponseDto<T>(boolean result, String message, T data) {

	@Builder
	public ResponseDto {
		result = true;
	}
}
