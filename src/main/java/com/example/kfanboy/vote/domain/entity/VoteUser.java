package com.example.kfanboy.vote.domain.entity;

import com.example.kfanboy.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_user")
public class VoteUser extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vote_user_id")
	private Long voteUserId;

	@NotNull
	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@NotNull
	@Column(name = "vote_item_id", nullable = false)
	private Long voteItemId;

	@Builder
	public VoteUser(Long voteUserId, Long memberId, Long voteItemId) {
		this.voteUserId = voteUserId;
		this.memberId = memberId;
		this.voteItemId = voteItemId;
	}
}

