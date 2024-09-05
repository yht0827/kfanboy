package com.example.kfanboy.vote.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.kfanboy.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote")
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "update vote set deleted_at=current_timestamp where vote_id = ?")
public class Vote extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vote_id")
	private Long voteId;

	@NotBlank
	@Column(name = "title", nullable = false)
	private String title;

	@NotNull
	@Column(name = "start_at", nullable = false)
	private LocalDateTime startAt;

	@NotNull
	@Column(name = "end_at")
	private LocalDateTime endAt;

	@NotNull
	@Column(name = "is_finished", nullable = false)
	private Boolean isFinished;

	@NotNull
	@Column(name = "vote_count", nullable = false)
	private Long voteCount;

	@NotNull
	@Column(name = "max_vote_count", nullable = false)
	private Long maxVoteCount;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Vote(Long voteId, String title, LocalDateTime startAt, LocalDateTime endAt, Boolean isFinished,
		Long voteCount, Long maxVoteCount, Long memberId, LocalDateTime deletedAt) {
		this.voteId = voteId;
		this.title = title;
		this.startAt = startAt;
		this.endAt = endAt;
		this.isFinished = isFinished;
		this.voteCount = voteCount;
		this.maxVoteCount = maxVoteCount;
		this.memberId = memberId;
		this.deletedAt = deletedAt;
	}
}
