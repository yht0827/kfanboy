package com.example.kfanboy.global.common.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record PageResponseDto<T>(List<T> list, long totalPage, int pageSize, long totalElements, int pageNumber) {
	public static <T> PageResponseDto<T> toDto(Page<T> response) {
		return PageResponseDto.<T>builder()
			.list(response.getContent())
			.totalPage(response.getTotalPages())
			.pageSize(response.getSize())
			.totalElements(response.getTotalElements())
			.pageNumber(response.getNumber())
			.build();
	}
}
