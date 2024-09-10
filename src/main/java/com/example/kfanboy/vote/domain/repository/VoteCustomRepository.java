package com.example.kfanboy.vote.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.vote.dto.VoteDetailResponseDto;
import com.example.kfanboy.vote.dto.VoteResponseDto;
import com.example.kfanboy.vote.search.VoteSearchCondition;

public interface VoteCustomRepository {

	Optional<VoteDetailResponseDto> findByVoteId(final Long voteId);

	PageResponseDto<VoteResponseDto> getVoteList(final VoteSearchCondition voteSearchCondition,
		final Pageable pageable);
}
