package com.example.kfanboy.vote.dto;

import com.example.kfanboy.vote.domain.entity.VoteItem;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record VoteItemRequestDto(@NotBlank String itemContent) {

	public VoteItem toEntity(final Long voteId) {
		return VoteItem.builder()
			.voteId(voteId)
			.itemContent(this.itemContent)
			.itemCount(0L)
			.build();
	}

}
