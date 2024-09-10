package com.example.kfanboy.vote.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.vote.domain.entity.Vote;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteDetailResponseDto(
	@NotNull Long voteId, @NotBlank String title, @NotNull LocalDateTime startAt,
	@NotNull LocalDateTime endAt, @NotNull Boolean isFinished, @NotNull Long voteCount, @NotNull Long maxVoteCount,
	@NotNull UserResponseDto member, @NotNull List<VoteItemResponseDto> voteItemList) {

	public static VoteDetailResponseDto toDto(final Vote vote, final List<VoteItemResponseDto> voteItemList,
		final Member member) {
		return VoteDetailResponseDto.builder()
			.voteId(vote.getVoteId())
			.title(vote.getTitle())
			.startAt(vote.getStartAt())
			.endAt(vote.getEndAt())
			.voteItemList(voteItemList)
			.isFinished(vote.getIsFinished())
			.voteCount(vote.getVoteCount())
			.maxVoteCount(vote.getMaxVoteCount())
			.member(UserResponseDto.toDto(member))
			.build();
	}
}
