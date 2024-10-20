package com.example.kfanboy.vote.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.vote.dto.VoteActRequestDto;
import com.example.kfanboy.vote.dto.VoteCreateRequestDto;
import com.example.kfanboy.vote.dto.VoteDetailResponseDto;
import com.example.kfanboy.vote.dto.VoteResponseDto;
import com.example.kfanboy.vote.search.VoteSearchCondition;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteApplication {

	private final VoteService voteService;

	private static final String VOTE_KEY_PREFIX = "vote_";

	public VoteDetailResponseDto getDetailVote(final Long voteId) {
		return voteService.getDetailVote(voteId);
	}

	public PageResponseDto<VoteResponseDto> getVoteList(final VoteSearchCondition voteSearchCondition,
		final Pageable pageable) {
		return voteService.getVoteList(voteSearchCondition, pageable);
	}

	public Long createVote(final Long memberId, final VoteCreateRequestDto voteCreateRequestDto) {
		return voteService.createVote(memberId, voteCreateRequestDto);
	}

	public Long voteAct(final Long memberId, final VoteActRequestDto voteActRequestDto) {
		return voteService.voteAct(VOTE_KEY_PREFIX + voteActRequestDto.voteId(), memberId, voteActRequestDto);
	}
}
