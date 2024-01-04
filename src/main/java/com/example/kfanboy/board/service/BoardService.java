package com.example.kfanboy.board.service;

import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.board.domain.repository.BoardRepository;
import com.example.kfanboy.board.dto.BoardCreateRequestDto;
import com.example.kfanboy.board.dto.BoardDeleteRequestDto;
import com.example.kfanboy.board.dto.BoardResponseDto;
import com.example.kfanboy.board.dto.BoardUpdateRequestDto;
import com.example.kfanboy.board.search.BoardSearchCondition;
import com.example.kfanboy.category.domain.entity.Category;
import com.example.kfanboy.category.domain.repository.CategoryRepository;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public BoardResponseDto findById(final Long boardId) {
		return boardRepository.findByBoardId(boardId)
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public PageResponseDto<BoardResponseDto> getBoardList(final BoardSearchCondition boardSearchCondition,
		final Pageable pageable) {
		return boardRepository.getBoardList(boardSearchCondition, pageable);
	}

	@Transactional
	public Long create(final Long memberId, final BoardCreateRequestDto boardCreateRequestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

		Category category = categoryRepository.findById(boardCreateRequestDto.categoryId())
			.orElseThrow(() -> new CustomException(ErrorMessage.CATEGORY_NOT_FOUND));

		return boardRepository.save(boardCreateRequestDto.toEntity(member, category)).getBoardId();
	}

	@Transactional
	public void update(final Long memberId, final BoardUpdateRequestDto boardUpdateRequestDto) {

		Board board = boardRepository.findById(boardUpdateRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		if (!Objects.equals(board.getMemberId(), memberId)) {
			throw new CustomException(ErrorMessage.BOARD_WRITER_NOT_MATCHED);
		}

		Category category = categoryRepository.findById(boardUpdateRequestDto.categoryId())
			.orElseThrow(() -> new CustomException(ErrorMessage.CATEGORY_NOT_FOUND));

		board.updateBoard(boardUpdateRequestDto, category.getCategoryId());
	}

	@Transactional
	public void delete(final BoardDeleteRequestDto boardDeleteRequestDto) {
		Board board = boardRepository.findById(boardDeleteRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		boardRepository.delete(board);
	}
}
