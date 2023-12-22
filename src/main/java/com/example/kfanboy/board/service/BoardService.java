package com.example.kfanboy.board.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.board.domain.repository.BoardRepository;
import com.example.kfanboy.board.dto.BoardCreateRequestDto;
import com.example.kfanboy.board.dto.BoardDeleteRequestDto;
import com.example.kfanboy.board.dto.BoardUpdateRequestDto;
import com.example.kfanboy.board.dto.BoardUpdateResponseDto;
import com.example.kfanboy.category.domain.entity.Category;
import com.example.kfanboy.category.domain.repository.CategoryRepository;
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

	@Transactional
	public void create(final Long memberId, final BoardCreateRequestDto boardCreateRequestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

		Category category = categoryRepository.findById(boardCreateRequestDto.categoryId())
			.orElseThrow(() -> new CustomException(ErrorMessage.CATEGORY_NOT_FOUND));

		Board board = boardRepository.save(boardCreateRequestDto.toEntity(member, category));

		Optional.of(board)
			.filter(b -> b.getBoardId() > 0)
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_CREATED));
	}

	@Transactional
	public BoardUpdateResponseDto update(final Long memberId, final BoardUpdateRequestDto boardUpdateRequestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

		Category category = categoryRepository.findById(boardUpdateRequestDto.categoryId())
			.orElseThrow(() -> new CustomException(ErrorMessage.CATEGORY_NOT_FOUND));

		Board board = boardRepository.findById(boardUpdateRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		return BoardUpdateResponseDto.toDto(board.updateBoard(boardUpdateRequestDto, category, member));
	}

	@Transactional
	public void delete(final BoardDeleteRequestDto boardDeleteRequestDto) {
		Board board = boardRepository.findById(boardDeleteRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		boardRepository.delete(board);
	}
}