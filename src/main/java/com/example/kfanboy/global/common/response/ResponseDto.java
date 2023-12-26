package com.example.kfanboy.global.common.response;

import lombok.Builder;

@Builder
public record ResponseDto<T>(boolean result, String message, T data) {
	public ResponseDto {
		result = true;
	}
}
