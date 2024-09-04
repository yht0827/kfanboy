package com.example.kfanboy.comment.controller;

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

import com.example.kfanboy.comment.dto.CommentCreateRequestDto;
import com.example.kfanboy.comment.dto.CommentDeleteRequestDto;
import com.example.kfanboy.comment.dto.CommentResponseDto;
import com.example.kfanboy.comment.dto.CommentUpdateRequestDto;
import com.example.kfanboy.comment.service.CommentService;
import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.common.annotation.CurrentUser;
import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comment")
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/list/{boardId}")
	public ResponseEntity<PageResponseDto<CommentResponseDto>> getList(
		final @PathVariable(value = "boardId") Long boardId, final Pageable pageable) {
		return success(commentService.getList(boardId, pageable));
	}

	@LoginCheck
	@PostMapping
	public ResponseEntity<ResponseDto<?>> createComment(
		@CurrentUser final Long memberId, @Valid @RequestBody final CommentCreateRequestDto commentCreateRequestDto) {
		return create(commentService.create(memberId, commentCreateRequestDto));
	}

	@LoginCheck
	@PatchMapping
	public ResponseEntity<ResponseDto<?>> updateComment(
		@CurrentUser final Long memberId, @Valid @RequestBody final CommentUpdateRequestDto commentUpdateRequestDto) {
		commentService.update(memberId, commentUpdateRequestDto);
		return success(SuccessMessage.UPDATE_COMMENT_SUCCESS);
	}

	@LoginCheck
	@DeleteMapping
	public ResponseEntity<ResponseDto<?>> deleteComment(
		@CurrentUser final Long memberId, @Valid @RequestBody final CommentDeleteRequestDto commentDeleteRequestDto) {
		commentService.delete(memberId, commentDeleteRequestDto);
		return success(SuccessMessage.DELETE_COMMENT_SUCCESS);
	}
}
