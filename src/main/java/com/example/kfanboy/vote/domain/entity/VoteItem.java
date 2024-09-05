package com.example.kfanboy.vote.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_item")
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "update vote_item set deleted_at=current_timestamp where vote_item_id = ?")
public class VoteItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vote_item_id")
	private Long voteItemId;

	@Column(name = "item_content", nullable = false)
	private String itemContent;

	@Column(name = "item_count", nullable = false)
	private Long itemCount;

	@Column(name = "vote_id", nullable = false)
	private Long voteId;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public VoteItem(Long voteItemId, String itemContent, Long itemCount, Long voteId, LocalDateTime deletedAt) {
		this.voteItemId = voteItemId;
		this.itemContent = itemContent;
		this.itemCount = itemCount;
		this.voteId = voteId;
		this.deletedAt = deletedAt;
	}
}
