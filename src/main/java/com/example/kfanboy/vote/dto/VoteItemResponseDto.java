package com.example.kfanboy.vote.dto;

import com.example.kfanboy.vote.domain.entity.VoteItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteItemResponseDto(@NotNull Long voteItemId, @NotBlank String itemContent, @NotNull Long itemCount) {

	public static VoteItemResponseDto toDto(final VoteItem voteItem) {
		return VoteItemResponseDto
			.builder()
			.voteItemId(voteItem.getVoteItemId())
			.itemContent(voteItem.getItemContent())
			.itemCount(voteItem.getItemCount())
			.build();
	}
}
