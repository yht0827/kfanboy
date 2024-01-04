package com.example.kfanboy.like.controller;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.common.annotation.CurrentUser;
import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.common.response.ResponseDto;
import com.example.kfanboy.like.dto.LikeRequestDto;
import com.example.kfanboy.like.dto.LikeResponseDto;
import com.example.kfanboy.like.service.LikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/like")
public class LikeController {

	private final LikeService likeService;

	@LoginCheck
	@GetMapping("/{boardId}")
	public ResponseEntity<ResponseDto<LikeResponseDto>> getLike(@CurrentUser Long memberId,
		@PathVariable final Long boardId) {
		return success(likeService.getLike(memberId, boardId));
	}

	@LoginCheck
	@PostMapping
	public ResponseEntity<ResponseDto<?>> updateLike(@CurrentUser final Long memberId,
		@Valid @RequestBody final LikeRequestDto likeRequestDto) {
		likeService.updateLike(memberId, likeRequestDto.boardId());
		return success(SuccessMessage.UPDATE_LIKE_SUCCESS);
	}
}
