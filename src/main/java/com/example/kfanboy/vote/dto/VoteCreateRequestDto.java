package com.example.kfanboy.vote.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.vote.domain.entity.Vote;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteCreateRequestDto(
	@NotBlank @Length(min = 1, max = 50) String title,
	@NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime startAt,
	@NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime endAt, @NotNull Long maxVoteCount, @NotNull List<VoteItemRequestDto> voteItemList) {

	public Vote toEntity(final Member member) {
		return Vote.builder()
			.title(this.title)
			.startAt(this.startAt)
			.endAt(this.endAt)
			.isFinished(false)
			.voteCount(0L)
			.maxVoteCount(this.maxVoteCount)
			.memberId(member.getMemberId())
			.build();
	}
}
