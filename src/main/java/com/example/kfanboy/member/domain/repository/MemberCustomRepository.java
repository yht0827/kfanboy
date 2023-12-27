package com.example.kfanboy.member.domain.repository;

import org.springframework.data.domain.Pageable;

import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.member.search.MemberSearchCondition;

public interface MemberCustomRepository {
	PageResponseDto<UserResponseDto> getUserList(final MemberSearchCondition memberSearchCondition,
		final Pageable pageable);
}
