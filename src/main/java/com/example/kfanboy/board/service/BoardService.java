package com.example.kfanboy.board.service;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.board.domain.entity.BoardDocument;
import com.example.kfanboy.board.domain.repository.BoardDocumentRepository;
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
	private final BoardDocumentRepository boardDocumentRepository;

	@Transactional(readOnly = true)
	public BoardResponseDto findById(final Long boardId) {
		BoardDocument boardDocument = boardDocumentRepository.findById(boardId)
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		return BoardResponseDto.toDto(boardDocument);
	}

	@Transactional(readOnly = true)
	public PageResponseDto<BoardResponseDto> getBoardList(final BoardSearchCondition boardSearchCondition,
		final Pageable pageable) {
		Page<BoardResponseDto> boardResponseDtos;

		if (!StringUtils.isEmpty(boardSearchCondition.title())) {
			boardResponseDtos = boardDocumentRepository.findAllByTitleContaining(boardSearchCondition.title(), pageable)
				.map(BoardResponseDto::toDto);
		} else if (!StringUtils.isEmpty(boardSearchCondition.nickName())) {
			boardResponseDtos = boardDocumentRepository.findAllByNickName(boardSearchCondition.nickName(), pageable)
				.map(BoardResponseDto::toDto);
		} else {
			boardResponseDtos = boardDocumentRepository.findAll(pageable).map(BoardResponseDto::toDto);
		}

		return PageResponseDto.toDto(boardResponseDtos);
	}

	@Transactional
	public Long create(final Long memberId, final BoardCreateRequestDto boardCreateRequestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

		Category category = categoryRepository.findById(boardCreateRequestDto.categoryId())
			.orElseThrow(() -> new CustomException(ErrorMessage.CATEGORY_NOT_FOUND));

		Board board = boardRepository.save(boardCreateRequestDto.toEntity(member, category));

		BoardDocument boardDocument = boardDocumentRepository.save(BoardDocument.toDoucment(board, member, category));

		return boardDocument.getBoardId();
	}

	@Transactional
	public void update(final Long memberId, final BoardUpdateRequestDto boardUpdateRequestDto) {
		Board board = boardRepository.findById(boardUpdateRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		BoardDocument boardDocument = boardDocumentRepository.findById(boardUpdateRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		if (!Objects.equals(board.getMemberId(), memberId) && !Objects.equals(boardDocument.getMemberId(), memberId)) {
			throw new CustomException(ErrorMessage.BOARD_WRITER_NOT_MATCHED);
		}

		Category category = categoryRepository.findById(boardUpdateRequestDto.categoryId())
			.orElseThrow(() -> new CustomException(ErrorMessage.CATEGORY_NOT_FOUND));

		board.updateBoard(boardUpdateRequestDto, category.getCategoryId());

		boardDocument.updateBoard(boardUpdateRequestDto);

		boardDocumentRepository.save(boardDocument);
	}

	@Transactional
	public void delete(final Long memberId, final BoardDeleteRequestDto boardDeleteRequestDto) {
		BoardDocument boardDocument = boardDocumentRepository.findById(boardDeleteRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		Board board = boardRepository.findById(boardDeleteRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		if (!Objects.equals(board.getMemberId(), memberId) && !Objects.equals(boardDocument.getMemberId(), memberId)) {
			throw new CustomException(ErrorMessage.BOARD_WRITER_NOT_MATCHED);
		}

		boardRepository.delete(board);

		boardDocumentRepository.delete(boardDocument);
	}
}
