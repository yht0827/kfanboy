package com.example.kfanboy.vote.dto;

import java.time.LocalDateTime;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.vote.domain.entity.Vote;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteResponseDto(
	@NotNull Long voteId, @NotBlank String title, @NotNull LocalDateTime startAt, @NotNull LocalDateTime endAt,
	@NotNull Long memberId, @NotBlank String memberName) {

	public static VoteResponseDto toDto(final Vote vote, final Member member) {
		return VoteResponseDto.builder()
			.voteId(vote.getVoteId())
			.title(vote.getTitle())
			.startAt(vote.getStartAt())
			.endAt(vote.getEndAt())
			.memberId(member.getMemberId())
			.memberName(member.getNickName())
			.build();
	}
}
