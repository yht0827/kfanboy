package com.example.kfanboy.global.common.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public record ResponseDto<T>(HttpStatus httpStatus, String message, T data) {
}
