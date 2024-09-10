package com.example.kfanboy.vote.dto;

import com.example.kfanboy.vote.domain.entity.VoteUser;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteActRequestDto(@NotNull Long voteId, @NotNull Long voteItemId) {

	public VoteUser toEntity(final Long voteItemId, final Long memberId) {
		return VoteUser.builder()
			.voteItemId(voteItemId)
			.memberId(memberId)
			.build();
	}
}
