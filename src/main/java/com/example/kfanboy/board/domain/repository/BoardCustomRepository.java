package com.example.kfanboy.board.domain.repository;

import org.springframework.data.domain.Pageable;

import com.example.kfanboy.board.dto.BoardResponseDto;
import com.example.kfanboy.board.search.BoardSearchCondition;
import com.example.kfanboy.global.common.response.PageResponseDto;

public interface BoardCustomRepository {
	PageResponseDto<BoardResponseDto> getBoardList(final BoardSearchCondition boardSearchCondition,
		final Pageable pageable);
}
