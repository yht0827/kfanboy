package com.example.kfanboy.board.controller;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/board")
public class BoardController {

	private final BoardService boardService;

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
		boardService.create(memberId, boardCreateRequestDto);
		return success(SuccessMessage.CREATE_BOARD_SUCCESS);
	}

	@LoginCheck
	@PutMapping
	public ResponseEntity<ResponseDto<BoardResponseDto>> updateBoard(
		@CurrentUser final Long memberId, @Valid @RequestBody final BoardUpdateRequestDto boardUpdateRequestDto) {
		return success(boardService.update(memberId, boardUpdateRequestDto),
			SuccessMessage.UPDATE_BOARD_SUCCESS);
	}

	@LoginCheck
	@DeleteMapping
	public ResponseEntity<ResponseDto<?>> deleteBoard(
		@Valid @RequestBody final BoardDeleteRequestDto boardDeleteRequestDto) {
		boardService.delete(boardDeleteRequestDto);
		return success(SuccessMessage.DELETE_BOARD_SUCCESS);
	}
}
