package com.example.kfanboy.vote.controller;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kfanboy.global.common.annotation.CurrentUser;
import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.common.response.ResponseDto;
import com.example.kfanboy.vote.dto.VoteActRequestDto;
import com.example.kfanboy.vote.dto.VoteCreateRequestDto;
import com.example.kfanboy.vote.dto.VoteDetailResponseDto;
import com.example.kfanboy.vote.dto.VoteResponseDto;
import com.example.kfanboy.vote.search.VoteSearchCondition;
import com.example.kfanboy.vote.service.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/vote")
public class VoteController {

	private final VoteService voteService;

	@GetMapping("/{voteId}")
	public ResponseEntity<ResponseDto<VoteDetailResponseDto>> getDetailVote(@PathVariable final Long voteId) {
		return success(voteService.getDetailVote(voteId));
	}

	@GetMapping("/list")
	public ResponseEntity<PageResponseDto<VoteResponseDto>> getVoteList(
		final VoteSearchCondition voteSearchCondition, final Pageable pageable) {
		return success(voteService.getVoteList(voteSearchCondition, pageable));
	}

	@LoginCheck
	@PostMapping
	public ResponseEntity<ResponseDto<?>> createVote(@CurrentUser final Long memberId,
		@Valid @RequestBody final VoteCreateRequestDto voteCreateRequestDto) {
		return create(voteService.createVote(memberId, voteCreateRequestDto));
	}

	@LoginCheck
	@PostMapping("/act")
	public ResponseEntity<ResponseDto<?>> voteAct(@CurrentUser final Long memberId,
		@Valid @RequestBody final VoteActRequestDto voteActRequestDto) {
		return create(voteService.voteAct(memberId, voteActRequestDto));
	}
}
