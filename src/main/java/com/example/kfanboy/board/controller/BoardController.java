package com.example.kfanboy.board.controller;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kfanboy.board.dto.BoardCreateRequestDto;
import com.example.kfanboy.board.dto.BoardDeleteRequestDto;
import com.example.kfanboy.board.dto.BoardResponseDto;
import com.example.kfanboy.board.dto.BoardUpdateRequestDto;
import com.example.kfanboy.board.search.BoardSearchCondition;
import com.example.kfanboy.board.service.BoardService;
import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.common.annotation.CurrentUser;
import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.common.response.ResponseDto;
import com.example.kfanboy.like.dto.LikeRequestDto;
import com.example.kfanboy.like.dto.LikeResponseDto;
import com.example.kfanboy.like.service.LikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/board")
public class BoardController {

	private final BoardService boardService;
	private final LikeService likeService;

	@GetMapping("/{boardId}")
	public ResponseEntity<ResponseDto<BoardResponseDto>> getBoard(@PathVariable final Long boardId) {
		return success(boardService.findById(boardId));
	}

	@GetMapping("/list")
	public ResponseEntity<PageResponseDto<BoardResponseDto>> getBoardList(
		final BoardSearchCondition boardSearchCondition, final Pageable pageable) {
		return success(boardService.getBoardList(boardSearchCondition, pageable));
	}

	@LoginCheck
	@PostMapping
	public ResponseEntity<ResponseDto<?>> createBoard(@CurrentUser final Long memberId,
		@Valid @RequestBody final BoardCreateRequestDto boardCreateRequestDto) {
		return create(boardService.create(memberId, boardCreateRequestDto));
	}

	@LoginCheck
	@PatchMapping
	public ResponseEntity<ResponseDto<?>> updateBoard(@CurrentUser final Long memberId,
		@Valid @RequestBody final BoardUpdateRequestDto boardUpdateRequestDto) {
		boardService.update(memberId, boardUpdateRequestDto);
		return success(SuccessMessage.UPDATE_BOARD_SUCCESS);
	}

	@LoginCheck
	@DeleteMapping
	public ResponseEntity<ResponseDto<?>> deleteBoard(
		@Valid @RequestBody final BoardDeleteRequestDto boardDeleteRequestDto) {
		boardService.delete(boardDeleteRequestDto);
		return success(SuccessMessage.DELETE_BOARD_SUCCESS);
	}

	@LoginCheck
	@GetMapping("/{boardId}/like")
	public ResponseEntity<ResponseDto<LikeResponseDto>> getLike(@CurrentUser Long memberId,
		@PathVariable final Long boardId) {
		return success(likeService.getLike(memberId, boardId));
	}

	@LoginCheck
	@PostMapping("/like")
	public ResponseEntity<ResponseDto<?>> updateLike(@CurrentUser final Long memberId,
		@Valid @RequestBody final LikeRequestDto likeRequestDto) {
		likeService.updateLike(memberId, likeRequestDto.boardId());
		return success(SuccessMessage.UPDATE_LIKE_SUCCESS);
	}
}
