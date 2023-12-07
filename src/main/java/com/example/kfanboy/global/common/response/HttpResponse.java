package com.example.kfanboy.global.common.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public record HttpResponse<T>(HttpStatus status, String message, T data) {
}
